package comp3111.examsystem.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    /**
     * test authenticate T,T
     * @author Wong Hon Yin
     */
    @Test
    public void testAuthenticate_Success() {
        // Test with correct username and password
        assertTrue(Manager.authenticate("mod", "password"), "Expected authentication to succeed with correct credentials.");
    }

    /**
     * test authenticate F,T
     * @author Wong Hon Yin
     */
    @Test
    public void testAuthenticate_Fail_Username() {
        // Test with incorrect username
        assertFalse(Manager.authenticate("wrongUser", "password"), "Expected authentication to fail with incorrect username.");
    }

    /**
     * test authenticate T,F
     * @author Wong Hon Yin
     */
    @Test
    public void testAuthenticate_Fail_Password() {
        // Test with incorrect password
        assertFalse(Manager.authenticate("mod", "wrongPassword"), "Expected authentication to fail with incorrect password.");
    }

    /**
     * test authenticate F,F
     * @author Wong Hon Yin
     */
    @Test
    public void testAuthenticate_Fail_Both() {
        // Test with both incorrect username and password
        assertFalse(Manager.authenticate("wrongUser", "wrongPassword"), "Expected authentication to fail with both incorrect username and password.");
    }


}