package simplerogue.engine.ui;

import lombok.AllArgsConstructor;
import simplerogue.engine.view.Screen;

/**
 * Layout relative to sides of the screen.
 *
 * @author Adam Wyłuda
 */
public class SideLayout implements Layout
{
    // Full screen by default
    private PositionCalculator yCalculator = new FixedStartCalculator(0);
    private PositionCalculator xCalculator = new FixedStartCalculator(0);
    private LengthCalculator heightCalculator = new ProportionalLengthCalculator(1.0);
    private LengthCalculator widthCalculator = new ProportionalLengthCalculator(1.0);

    private SideLayout()
    {
    }

    /**
     * Creates full screen layout.
     */
    public static SideLayout createFullScreenLayout()
    {
        return new SideLayout();
    }

    public static SideLayout createCenteredLayout(Layout layout)
    {
        return builder()
                .proportionalToTop(0.5)
                .yCentered()
                .proportionalToLeft(0.5)
                .xCentered()
                .delegateHeight(layout)
                .delegateWidth(layout)
                .build();
    }

    public static SideLayout createFixedPositionLayout(int y, int x, int height, int width)
    {
        return builder()
                .fixedToTop(y)
                .fixedToLeft(x)
                .fixedHeight(height)
                .fixedWidth(width)
                .build();
    }

    public static SideLayout createLeftBorderLayout(double proportion)
    {
        return builder()
                .proportionalToTop(0.0)
                .proportionalToLeft(0.0)
                .proportionalHeight(1.0)
                .proportionalWidth(proportion)
                .build();
    }

    public static SideLayout createLeftBorderLayout(int size)
    {
        return builder()
                .proportionalToTop(0.0)
                .proportionalToLeft(0.0)
                .proportionalHeight(1.0)
                .fixedWidth(size)
                .build();
    }

    public static SideLayout createTopBorderLayout(double proportion)
    {
        return builder()
                .proportionalToTop(0.0)
                .proportionalToLeft(0.0)
                .proportionalHeight(proportion)
                .proportionalWidth(1.0)
                .build();
    }

    public static SideLayout createTopBorderLayout(int size)
    {
        return builder()
                .proportionalToTop(0.0)
                .proportionalToLeft(0.0)
                .fixedHeight(size)
                .proportionalWidth(1.0)
                .build();
    }

    public static SideLayout createRightBorderLayout(double proportion)
    {
        return builder()
                .proportionalToTop(0.0)
                .proportionalToRight(0.0)
                .proportionalHeight(1.0)
                .proportionalWidth(proportion)
                .build();
    }

    public static SideLayout createRightBorderLayout(int size)
    {
        return builder()
                .proportionalToTop(0.0)
                .proportionalToRight(0.0)
                .proportionalHeight(1.0)
                .fixedWidth(size)
                .build();
    }

    public static SideLayout createBottomBorderLayout(double proportion)
    {
        return builder()
                .proportionalToBottom(0.0)
                .proportionalToLeft(0.0)
                .proportionalHeight(proportion)
                .proportionalWidth(1.0)
                .build();
    }

    public static SideLayout createBottomBorderLayout(int size)
    {
        return builder()
                .proportionalToBottom(0.0)
                .proportionalToLeft(0.0)
                .fixedHeight(size)
                .proportionalWidth(1.0)
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    @Override
    public int getY(Screen screen)
    {
        return yCalculator.calculate(screen.getHeight(), getHeight(screen));
    }

    @Override
    public int getX(Screen screen)
    {
        return xCalculator.calculate(screen.getWidth(), getWidth(screen));
    }

    @Override
    public int getHeight(Screen screen)
    {
        return heightCalculator.calculate(screen, screen.getHeight());
    }

    @Override
    public int getWidth(Screen screen)
    {
        return widthCalculator.calculate(screen, screen.getWidth());
    }

    public static class Builder
    {
        SideLayout result = new SideLayout();

        private Builder()
        {
        }
        
        public Builder fixedToLeft(int distance)
        {
            result.xCalculator = new FixedStartCalculator(distance);
            return this;
        }

        public Builder fixedToTop(int distance)
        {
            result.yCalculator = new FixedStartCalculator(distance);
            return this;
        }

        public Builder fixedToRight(int distance)
        {
            result.xCalculator = new FixedEndCalculator(distance);
            return this;
        }

        public Builder fixedToBottom(int distance)
        {
            result.yCalculator = new FixedEndCalculator(distance);
            return this;
        }

        public Builder proportionalToLeft(double proportion)
        {
            result.xCalculator = new ProportionalStartCalculator(proportion);
            return this;
        }

        public Builder proportionalToTop(double proportion)
        {
            result.yCalculator = new ProportionalStartCalculator(proportion);
            return this;
        }

        public Builder proportionalToRight(double proportion)
        {
            result.xCalculator = new ProportionalEndCalculator(proportion);
            return this;
        }

        public Builder proportionalToBottom(double proportion)
        {
            result.yCalculator = new ProportionalEndCalculator(proportion);
            return this;
        }

        public Builder xCentered()
        {
            result.xCalculator = new CenteredCalculator(result.xCalculator);
            return this;
        }

        public Builder yCentered()
        {
            result.yCalculator = new CenteredCalculator(result.yCalculator);
            return this;
        }
        
        public Builder fixedWidth(int length)
        {
            result.widthCalculator = new FixedLengthCalculator(length);
            return this;
        }
        
        public Builder fixedHeight(int length)
        {
            result.heightCalculator = new FixedLengthCalculator(length);
            return this;
        }
        
        public Builder proportionalWidth(double proportion)
        {
            result.widthCalculator = new ProportionalLengthCalculator(proportion);
            return this;
        }
        
        public Builder proportionalHeight(double proportion)
        {
            result.heightCalculator = new ProportionalLengthCalculator(proportion);
            return this;
        }

        public Builder delegateWidth(Layout layout)
        {
            result.widthCalculator = new DelegateWidthCalculator(layout);
            return this;
        }

        public Builder delegateHeight(Layout layout)
        {
            result.heightCalculator = new DelegateHeightCalculator(layout);
            return this;
        }

        public SideLayout build()
        {
            return result;
        }
    }

    static interface PositionCalculator
    {
        int calculate(int screenLength, int length);
    }

    static interface LengthCalculator
    {
        int calculate(Screen screen, int fullLength);
    }

    @AllArgsConstructor
    static class FixedStartCalculator implements PositionCalculator
    {
        private int distance;

        @Override
        public int calculate(int screenLength, int length)
        {
            return distance;
        }
    }

    @AllArgsConstructor
    static class FixedEndCalculator implements PositionCalculator
    {
        private int distance;

        @Override
        public int calculate(int screenLength, int length)
        {
            return screenLength - distance - length;
        }
    }

    @AllArgsConstructor
    static class ProportionalStartCalculator implements PositionCalculator
    {
        private double proportion;

        @Override
        public int calculate(int screenLength, int length)
        {
            return (int) (proportion * screenLength);
        }
    }

    @AllArgsConstructor
    static class ProportionalEndCalculator implements PositionCalculator
    {
        private double proportion;

        @Override
        public int calculate(int screenLength, int length)
        {
            return (int) ((1.0 - proportion) * screenLength - length);
        }
    }

    @AllArgsConstructor
    static class CenteredCalculator implements PositionCalculator
    {
        private PositionCalculator delegate;

        @Override
        public int calculate(int screenLength, int length)
        {
            return delegate.calculate(screenLength, length) - length / 2;
        }
    }

    @AllArgsConstructor
    static class FixedLengthCalculator implements LengthCalculator
    {
        private int distance;

        @Override
        public int calculate(Screen screen, int fullLength)
        {
            return distance;
        }
    }

    @AllArgsConstructor
    static class ProportionalLengthCalculator implements LengthCalculator
    {
        private double proportion;

        @Override
        public int calculate(Screen screen, int fullLength)
        {
            return (int) (proportion * fullLength);
        }
    }

    @AllArgsConstructor
    static class DelegateHeightCalculator implements LengthCalculator
    {
        private Layout delegate;

        @Override
        public int calculate(Screen screen, int fullLength)
        {
            return delegate.getHeight(screen);
        }
    }

    @AllArgsConstructor
    static class DelegateWidthCalculator implements LengthCalculator
    {
        private Layout delegate;

        @Override
        public int calculate(Screen screen, int fullLength)
        {
            return delegate.getWidth(screen);
        }
    }
}
