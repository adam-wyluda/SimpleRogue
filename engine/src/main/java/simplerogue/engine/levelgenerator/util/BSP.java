package simplerogue.engine.levelgenerator.util;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Level;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.util.RandomUtil;

/**
 * @author Adam Wyłuda
 */
@Data
public class BSP
{
    private Node root;
    private RoomProvider roomProvider;
    private CorridorPrototype corridorPrototype;

    public static BSP create(int height, int width, Config config)
    {
        BSP bsp = new BSP();

        bsp.root = Node.createRecursively(height, width, config);

        return bsp;
    }

    public static BSP create(int height, int width)
    {
        return create(height, width, Config.create());
    }

    public static BSP create(Level level)
    {
        return create(level.getArea().getHeight(), level.getArea().getWidth());
    }

    public static BSP create(Level level, Config config)
    {
        return create(level.getArea().getHeight(), level.getArea().getWidth(), config);
    }

    public void makeLevel(Level level)
    {
        Preconditions.checkState(roomProvider != null, "Room provider must be supplied");
        Preconditions.checkState(corridorPrototype != null, "Corridor prototype must be supplied");

        root.makeRooms(level, roomProvider, corridorPrototype);
    }

    @Data
    @AllArgsConstructor(staticName = "create")
    public static class CorridorPrototype
    {
        private final Prototype fillPrototype;
        private final Prototype borderPrototype;
    }

    public static interface RoomProvider
    {
        Room provide(Level level, Node node);
    }

    @AllArgsConstructor(staticName = "create")
    public static class RectangleRoomProvider implements RoomProvider
    {
        private final Prototype fillPrototype;
        private final Prototype borderPrototype;

        @Override
        public Room provide(Level level, Node node)
        {
            RectangleRoom room = RectangleRoom.createRandom(
                    node.getStartY() + 1, node.getEndY() - 1,
                    node.getStartX() + 1, node.getEndX() - 1,
                    fillPrototype, borderPrototype,
                    level);

            return room;
        }
    }

    @Data
    @NoArgsConstructor(staticName = "create")
    public static class Config
    {
        private int depth = 3;
        private double skipChance = 0.0;
        private int separatorVariation = 0;
    }

    @Data
    public static class Node
    {
        public static enum Type
        {
            VERTICAL
                    {
                        @Override
                        Type getOpposite()
                        {
                            return HORIZONTAL;
                        }
                    },
            HORIZONTAL
                    {
                        @Override
                        Type getOpposite()
                        {
                            return VERTICAL;
                        }
                    };

            abstract Type getOpposite();

            static Type random()
            {
                return RandomUtil.randomBoolean() ? VERTICAL : HORIZONTAL;
            }
        }

        private int startY;
        private int endY;
        private int startX;
        private int endX;

        private int separator;
        private Type type;
        private int depth;

        private Optional<Node> left;
        private Optional<Node> right;

        private Room room;
        private int roomStartY;
        private int roomEndY;
        private int roomStartX;
        private int roomEndX;

        private Node(int startY, int endY,
                     int startX, int endX,
                     int separator,
                     Type type)
        {
            this.startY = startY;
            this.endY = endY;
            this.startX = startX;
            this.endX = endX;
            this.separator = separator;
            this.type = type;
        }

        protected static Node createRecursively(int height, int width, Config config)
        {
            Node root = new Node(
                    0, height,
                    0, width,
                    separate(0, height),
                    Type.VERTICAL);
            root.setDepth(config.getDepth());

            root.left = createRecursivelyLeft(root, config);
            root.right = createRecursivelyRight(root, config);

            return root;
        }

        protected static Optional<Node> createRecursivelyLeft(Node parent, Config config)
        {
            if (parent.depth == 0)
            {
                return Optional.absent();
            }

            Node node;

            if (parent.type == Type.VERTICAL)
            {
                node = new Node(
                        parent.startY, parent.separator,
                        parent.startX, parent.endX,
                        separate(parent.startX, parent.endX),
                        parent.type.getOpposite());
            }
            else
            {
                node = new Node(
                        parent.startY, parent.endY,
                        parent.startX, parent.separator,
                        separate(parent.startY, parent.endY),
                        parent.type.getOpposite());
            }

            configureBeforeChildren(parent, node, config);

            node.left = createRecursivelyLeft(node, config);
            node.right = createRecursivelyRight(node, config);

            return Optional.of(node);
        }

        protected static Optional<Node> createRecursivelyRight(Node parent, Config config)
        {
            if (parent.depth == 0)
            {
                return Optional.absent();
            }

            Node node;

            if (parent.type == Type.VERTICAL)
            {
                node = new Node(
                        parent.separator, parent.endY,
                        parent.startX, parent.endX,
                        separate(parent.startX, parent.endX),
                        parent.type.getOpposite());
            }
            else
            {
                node = new Node(
                        parent.startY, parent.endY,
                        parent.separator, parent.endX,
                        separate(parent.startY, parent.endY),
                        parent.type.getOpposite());
            }

            configureBeforeChildren(parent, node, config);

            node.left = createRecursivelyLeft(node, config);
            node.right = createRecursivelyRight(node, config);

            return Optional.of(node);
        }

        protected static void configureBeforeChildren(Node parent, Node node, Config config)
        {
            node.setDepth(parent.depth - 1);

            final double configSkipChance = config.getSkipChance();
            int levelsSkipped = 0;

            while (levelsSkipped < node.depth)
            {
                // Increase chance as depth approaches zero
                int level = node.depth + levelsSkipped;
                double skipChance = 1.0 / Math.pow(2.0 - configSkipChance, Math.pow(2.0, level));

                if (RandomUtil.randomBoolean(skipChance))
                {
                    levelsSkipped++;
                }
                else
                {
                    break;
                }
            }

            node.depth -= levelsSkipped;

            int separatorVariation = config.getSeparatorVariation();
            separatorVariation /= Math.pow(2, config.getDepth() - node.getDepth());

            node.separator += RandomUtil.randomInt(-separatorVariation, +separatorVariation);
        }

        private static int separate(int min, int max)
        {
            return (min + max) / 2;
        }

        public boolean isLeaf()
        {
            return !left.isPresent();
        }

        protected void makeRooms(Level level, RoomProvider roomProvider, CorridorPrototype corridorPrototype)
        {
            if (isLeaf())
            {
                room = roomProvider.provide(level, this);

                room.fill();

                roomStartY = room.randomBorderPoint(Direction.N).getY();
                roomEndY = room.randomBorderPoint(Direction.S).getY();
                roomStartX = room.randomBorderPoint(Direction.W).getX();
                roomEndX = room.randomBorderPoint(Direction.E).getX();
            }
            else
            {
                left.get().makeRooms(level, roomProvider, corridorPrototype);
                right.get().makeRooms(level, roomProvider, corridorPrototype);

                connect(level, corridorPrototype);

                roomStartY = Math.min(left.get().getRoomStartY(), right.get().getRoomStartY());
                roomEndY = Math.max(left.get().getRoomEndY(), right.get().getRoomEndY());
                roomStartX = Math.min(left.get().getRoomStartX(), right.get().getRoomStartX());
                roomEndX = Math.max(left.get().getRoomEndX(), right.get().getRoomEndX());
            }
        }

        protected void connect(Level level, CorridorPrototype corridorPrototype)
        {
            Preconditions.checkState(!isLeaf(), "Cannot connect leaf node");

            if (type == Type.VERTICAL)
            {
                int corridorStartX = RandomUtil.randomInt(left.get().roomStartX, left.get().roomEndX);
                int corridorEndX = RandomUtil.randomInt(right.get().roomStartX, right.get().roomEndX);

                GeneratorUtil.drawCorridorVertical(level,
                        corridorPrototype.fillPrototype, Optional.fromNullable(corridorPrototype.getBorderPrototype()),
                        separator, corridorStartX, corridorEndX);
            }
            else if (type == Type.HORIZONTAL)
            {
                int corridorStartY = RandomUtil.randomInt(left.get().roomStartY, left.get().roomEndY);
                int corridorEndY = RandomUtil.randomInt(right.get().roomStartY, right.get().roomEndY);

                GeneratorUtil.drawCorridorHorizontal(level,
                        corridorPrototype.fillPrototype, Optional.fromNullable(corridorPrototype.getBorderPrototype()),
                        separator, corridorStartY, corridorEndY);
            }
        }
    }
}
