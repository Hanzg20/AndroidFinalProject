package algonquin.cst2335.androidfinalproject.sunLookup.Model;




import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class SunriseSunsetData implements Parcelable {
    private String date;
    private String sunrise;
    private String sunset;
    private String firstLight;
    private String lastLight;
    private String dawn;
    private String dusk;
    private String solarNoon;
    private String goldenHour;
    private String dayLength;
    private String timezone;
    private int utcOffset;

    @SuppressLint("NotConstructor")
    public void SunriseSunsetData(String date, String sunrise, String sunset, String firstLight, String lastLight, String dawn, String dusk, String solarNoon, String goldenHour, String dayLength, String timezone, int utcOffset) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.firstLight = firstLight;
        this.lastLight = lastLight;
        this.dawn = dawn;
        this.dusk = dusk;
        this.solarNoon = solarNoon;
        this.goldenHour = goldenHour;
        this.dayLength = dayLength;
        this.timezone = timezone;
        this.utcOffset = utcOffset;
    }

    protected SunriseSunsetData(Parcel in) {
        date = in.readString();
        sunrise = in.readString();
        sunset = in.readString();
        firstLight = in.readString();
        lastLight = in.readString();
        dawn = in.readString();
        dusk = in.readString();
        solarNoon = in.readString();
        goldenHour = in.readString();
        dayLength = in.readString();
        timezone = in.readString();
        utcOffset = in.readInt();
    }

    public static final Creator<SunriseSunset> CREATOR = new Creator<SunriseSunset>() {
        @Override
        public SunriseSunset createFromParcel(Parcel in) {
            return new SunriseSunset(in);
        }

        @Override
        public SunriseSunset[] newArray(int size) {
            return new SunriseSunset[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getFirstLight() {
        return firstLight;
    }

    public String getLastLight() {
        return lastLight;
    }

    public String getDawn() {
        return dawn;
    }

    public String getDusk() {
        return dusk;
    }

    public String getSolarNoon() {
        return solarNoon;
    }

    public String getGoldenHour() {
        return goldenHour;
    }

    public String getDayLength() {
        return dayLength;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(sunrise);
        dest.writeString(sunset);
        dest.writeString(firstLight);
        dest.writeString(lastLight);
        dest.writeString(dawn);
        dest.writeString(dusk);
        dest.writeString(solarNoon);
        dest.writeString(goldenHour);
        dest.writeString(dayLength);
        dest.writeString(timezone);
        dest.writeInt(utcOffset);
    }
}
