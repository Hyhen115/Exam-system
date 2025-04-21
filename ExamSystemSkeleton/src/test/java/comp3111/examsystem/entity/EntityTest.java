package comp3111.examsystem.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityTest {

    private Entity entity;

    /**
     * set up entity
     * @author Wong Hon Yin
     */
    @BeforeEach
    public void setUp() {
        // Initialize an Entity object before each test
        entity = new Entity(1L);
    }

    /**
     * test default constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testDefaultConstructor() {
        Entity defaultEntity = new Entity();

        assertEquals(0L, defaultEntity.getId()); // Default ID should be 0
    }

    /**
     * test parameter constructor
     * @author Wong Hon Yin
     */
    @Test
    public void testParameterizedConstructorAndGetter() {
        assertEquals(1L, entity.getId());
    }

    /**
     * test setter
     * @author Wong Hon Yin
     */
    @Test
    public void testSetter() {
        entity.setId(2L);
        assertEquals(2L, entity.getId());
    }

    /**
     * test compare function
     * @author Wong Hon Yin
     */
    @Test
    public void testCompareTo() {
        Entity entity1 = new Entity(1L);
        Entity entity2 = new Entity(2L);
        Entity entity3 = new Entity(1L);

        assertTrue(entity1.compareTo(entity2) < 0); // entity1 should be less than entity2
        assertTrue(entity2.compareTo(entity1) > 0); // entity2 should be greater than entity1
        assertTrue(entity1.compareTo(entity3) == 0); // entity1 should be equal to entity3
    }
}