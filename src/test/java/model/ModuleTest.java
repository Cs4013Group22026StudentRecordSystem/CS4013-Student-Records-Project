package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks module identity and the properties of the programme-module association.
 */
class ModuleTest {

    private final Programme cs = new Programme("LM121", "Computer Science",
            ProgrammeLevel.UNDERGRADUATE);
    private final Programme csSystems = new Programme("LM051", "Computer Systems",
            ProgrammeLevel.UNDERGRADUATE);

    @Test
    @DisplayName("modules are identified by code, not by object identity")
    void modulesAreEqualByCode() {
        Module one = new Module("CS4013", "Object Oriented Development", 6);
        Module two = new Module("CS4013", "Object Oriented Development", 6);
        Module other = new Module("CS4012", "Operating Systems", 6);

        assertEquals(one, two);
        assertEquals(one.hashCode(), two.hashCode());
        assertNotEquals(one, other);
    }

    @Test
    @DisplayName("a module may be recorded without a responsible faculty member")
    void facultyIsOptional() {
        Module module = new Module("CS4013", "Object Oriented Development", 6);
        assertFalse(module.hasFaculty());
        assertNull(module.getFaculty());
    }

    @Test
    @DisplayName("invalid modules are rejected")
    void rejectsInvalidModules() {
        assertThrows(IllegalArgumentException.class,
                () -> new Module("", "Title", 6));
        assertThrows(IllegalArgumentException.class,
                () -> new Module("CS4013", " ", 6));
        assertThrows(IllegalArgumentException.class,
                () -> new Module("CS4013", "Title", 0));
    }

    @Test
    @DisplayName("the same module can be shared by several programmes")
    void moduleCanBeSharedBetweenProgrammes() {
        Module shared = new Module("CS4013", "Object Oriented Development", 6);

        ProgrammeModule onCs = new ProgrammeModule(cs, shared, 2, Semester.AUTUMN, true);
        ProgrammeModule onSystems = new ProgrammeModule(csSystems, shared, 2, Semester.AUTUMN, false);

        assertEquals(shared, onCs.getModule());
        assertEquals(shared, onSystems.getModule());
        assertTrue(onCs.isMandatory());
        assertFalse(onSystems.isMandatory(), "compulsory status depends on the programme");
    }

    @Test
    @DisplayName("associations are identified by programme and module")
    void associationsAreEqualByProgrammeAndModule() {
        Module module = new Module("CS4013", "Object Oriented Development", 6);

        ProgrammeModule autumn = new ProgrammeModule(cs, module, 2, Semester.AUTUMN, true);
        ProgrammeModule spring = new ProgrammeModule(cs, module, 3, Semester.SPRING, false);
        ProgrammeModule otherProgramme = new ProgrammeModule(csSystems, module, 2,
                Semester.AUTUMN, true);

        assertEquals(autumn, spring, "year and semester describe the membership, not its identity");
        assertEquals(autumn.hashCode(), spring.hashCode());
        assertNotEquals(autumn, otherProgramme);
    }

    @Test
    @DisplayName("invalid associations are rejected")
    void rejectsInvalidAssociations() {
        Module module = new Module("CS4013", "Object Oriented Development", 6);

        assertThrows(IllegalArgumentException.class,
                () -> new ProgrammeModule(null, module, 1, Semester.AUTUMN, true));
        assertThrows(IllegalArgumentException.class,
                () -> new ProgrammeModule(cs, null, 1, Semester.AUTUMN, true));
        assertThrows(IllegalArgumentException.class,
                () -> new ProgrammeModule(cs, module, 1, null, true));
        assertThrows(IllegalArgumentException.class,
                () -> new ProgrammeModule(cs, module, 0, Semester.AUTUMN, true));
    }
}
