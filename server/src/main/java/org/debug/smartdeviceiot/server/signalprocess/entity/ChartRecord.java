package org.debug.smartdeviceiot.server.signalprocess.entity;

public class ChartRecord<T1, T2> {

    /*------------------------ FIELDS REGION ------------------------*/
    private T1 x;
    private T2 y;

    /*------------------------ METHODS REGION ------------------------*/
    public ChartRecord(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }

    public T1 getX() {
        return x;
    }

    public void setX(T1 x) {
        this.x = x;
    }

    public T2 getY() {
        return y;
    }

    public void setY(T2 y) {
        this.y = y;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        ChartRecord<?, ?> that = (ChartRecord<?, ?>) o;
//
//        return new EqualsBuilder()
//                .append(x, that.x)
//                .append(y, that.y)
//                .isEquals();
//    }

//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 37)
//                .append(x)
//                .append(y)
//                .toHashCode();
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this)
//                .append("axisX", x)
//                .append("axisY", y)
//                .toString();
//    }
}
    