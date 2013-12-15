package uk.co.alynn.one;

public abstract class Angle {
    private static final class AngleDegrees extends Angle {
        private final double _deg;

        public AngleDegrees(double deg) {
            _deg = deg;
        }

        @Override
        public double getDegrees() {
            // TODO Auto-generated method stub
            return _deg;
        }

        @Override
        public double getRadians() {
            // TODO Auto-generated method stub
            return _deg * (Math.PI / 180.0);
        }
    }

    private static final class AngleRadians extends Angle {
        private final double _rad;

        public AngleRadians(double rad) {
            _rad = rad;
        }

        @Override
        public double getDegrees() {
            // TODO Auto-generated method stub
            return _rad * (180.0 / Math.PI);
        }

        @Override
        public double getRadians() {
            // TODO Auto-generated method stub
            return _rad;
        }
    }

    abstract public double getDegrees();

    abstract public double getRadians();

    public double cosine() {
        return Math.cos(getRadians());
    }

    public double sine() {
        return Math.sin(getRadians());
    }

    @Override
    public String toString() {
        return getDegrees() + " degrees";
    }

    public static Angle degrees(double deg) {
        return new AngleDegrees(deg);
    }

    public static Angle radians(double rad) {
        return new AngleRadians(rad);
    }
}
