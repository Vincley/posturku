package com.capstone.posturku.tracker

import com.capstone.posturku.data.pose.Person

data class Track(
    val person: Person,
    val lastTimestamp: Long
)
