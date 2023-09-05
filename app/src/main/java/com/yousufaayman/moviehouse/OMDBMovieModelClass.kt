package com.yousufaayman.moviehouse

import android.os.Parcel
import android.os.Parcelable

data class OMDBMovieModelClass(
    val Title: String,
    val Rated: String,
    val Released: String,
    val Runtime: String?,
    val Genre: String,
    val Director: String,
    val Actors: String,
    val Plot: String,
    val Poster: String,
    val Metascore: String
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Rated)
        parcel.writeString(Released)
        parcel.writeString(Runtime)
        parcel.writeString(Genre)
        parcel.writeString(Director)
        parcel.writeString(Actors)
        parcel.writeString(Plot)
        parcel.writeString(Poster)
        parcel.writeString(Metascore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OMDBMovieModelClass> {
        override fun createFromParcel(parcel: Parcel): OMDBMovieModelClass {
            return OMDBMovieModelClass(parcel)
        }

        override fun newArray(size: Int): Array<OMDBMovieModelClass?> {
            return arrayOfNulls(size)
        }
    }
}
