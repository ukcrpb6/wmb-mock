package com.ibm.broker.plugin;

import com.ibm.broker.trace.Trace;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

public class MbInterval {
    int sign;
    int years;
    int months;
    int days;
    int hours;
    int minutes;
    int milliseconds;

    public int getSign() {
        return this.sign;
    }

    public int getYears() {
        if (this.years == -1) {
            return 0;
        }

        return this.years;
    }

    boolean isSetYears() {
        return this.years != -1;
    }

    public int getMonths() {
        if (this.months == -1) {
            return 0;
        }

        return this.months;
    }

    boolean isSetMonths() {
        return this.months != -1;
    }

    public int getDays() {
        if (this.days == -1) {
            return 0;
        }

        return this.days;
    }

    boolean isSetDays() {
        return this.days != -1;
    }

    public int getHours() {
        if (this.hours == -1) {
            return 0;
        }

        return this.hours;
    }

    boolean isSetHours() {
        return this.hours != -1;
    }

    public int getMinutes() {
        if (this.minutes == -1) {
            return 0;
        }

        return this.minutes;
    }

    boolean isSetMinutes() {
        return this.minutes != -1;
    }

    public int getSeconds() {
        if (this.milliseconds == -1) {
            return 0;
        }

        return this.milliseconds / 1000000;
    }

    boolean isSetSeconds() {
        return this.milliseconds != -1;
    }

    public int getMilliseconds() {
        if (this.milliseconds == -1) {
            return 0;
        }

        return this.milliseconds;
    }

    public MbInterval(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
        if (Trace.isOn)
            Trace.logNamedEntryData(this, "MbInterval", " sign: " + paramInt1 + " years: " + paramInt2 + " months: " + paramInt3 + " days: " + paramInt4 + " hours: " + paramInt5 + " minutes: " + paramInt6 + " milliseconds: " + paramInt7);
        this.sign = paramInt1;
        this.years = paramInt2;
        this.months = paramInt3;
        this.days = paramInt4;
        this.hours = paramInt5;
        this.minutes = paramInt6;
        this.milliseconds = paramInt7;
        if (Trace.isOn) Trace.logNamedExit(this, "MbInterval");
    }

    public MbInterval(Duration paramDuration) {
        if (Trace.isOn) Trace.logNamedEntryData(this, "MbInterval", "duration: " + paramDuration);
        this.sign = paramDuration.getSign();
        this.years = paramDuration.getYears();
        this.months = paramDuration.getMonths();
        this.days = paramDuration.getDays();
        this.hours = paramDuration.getHours();
        this.minutes = paramDuration.getMinutes();
        this.milliseconds = (paramDuration.getSeconds() * 1000000);
        if (Trace.isOn) Trace.logNamedExit(this, "MbInterval");
    }

    public Duration getXMLDuration() throws DatatypeConfigurationException {
        if (Trace.isOn) Trace.logNamedEntry(this, "getXMLDuration");
        Duration localDuration;
        if ((isSetDays()) || (isSetHours()) || (isSetMinutes()) || (isSetSeconds())) {
            if ((isSetYears()) || (isSetMonths())) {
                localDuration = DatatypeFactory.newInstance().newDuration(this.sign > 0, getYears(), getMonths(), getDays(), getHours(), getMinutes(), getMilliseconds() / 10000);
            } else {
                localDuration = DatatypeFactory.newInstance().newDurationDayTime(this.sign > 0, getDays(), getHours(), getMinutes(), getMilliseconds() / 10000);
            }
        } else {
            localDuration = DatatypeFactory.newInstance().newDurationYearMonth(this.sign > 0, getYears(), getMonths());
        }
        if (Trace.isOn) Trace.logNamedExitData(this, "getXMLDuration", "duration: " + localDuration);
        return localDuration;
    }
}