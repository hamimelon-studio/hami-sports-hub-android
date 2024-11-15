package com.mikeapp.sportsmate.dashboard

import com.mikeapp.sportsmate.data.room.nba.TeamEvent
import com.mikeapp.sportsmate.util.LocalDateTimeUtil
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.MILLIS_PER_DAY
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.MILLIS_PER_WEEK
import java.util.*
import java.util.Calendar.getInstance

object GenerateCalendarHelper {
    fun generateCalendarDays(events: List<TeamEvent>, scheduleWeeks: Int, weekStartFromMonday: Boolean): List<List<Calendar>> {
        val today = getInstance()
        val firstDay = if (weekStartFromMonday) {
            LocalDateTimeUtil.getMondayOfWeek(today)
        } else {
            LocalDateTimeUtil.getSundayOfWeek(today)
        }

        val maxWeekOffset = getLastWeekWithEvents(firstDay, events, scheduleWeeks)
        val calendarDays = mutableListOf<List<Calendar>>()
        val firstDayId = LocalDateTimeUtil.getDayIdentifier(firstDay)
        for (i in 0..maxWeekOffset) {
            val daysAWeek = mutableListOf<Calendar>()
            for (weekDay in 0 until 7) {
                daysAWeek.add(getInstance().apply {
                    timeInMillis = firstDayId + (i * 7 + weekDay) * MILLIS_PER_DAY
                })
            }
            calendarDays.add(daysAWeek)
        }
        return calendarDays
    }

    private fun getLastWeekWithEvents(firstDay: Calendar, events: List<TeamEvent>, maxNoOfWeek: Int): Int {
        for (i in 1 until maxNoOfWeek) {
            val firstDayOfThatWeek = getInstance().apply {
                timeInMillis = firstDay.timeInMillis + MILLIS_PER_WEEK * i
            }
            val eventsLeft = events.filter {
                it.unixTimeStamp > firstDayOfThatWeek.timeInMillis
            }
            if (eventsLeft.isEmpty()) {
                return i - 1
            }
        }
        return maxNoOfWeek - 1
    }
}