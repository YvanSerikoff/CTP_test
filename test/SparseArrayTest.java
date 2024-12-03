import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SparseArrayTest {

    public static final int CUSTOM_LENGTH = 20;
    SparseArray<String> array;
    SparseArray<List<Integer>> empty_array = new SparseArray<>();
    SparseArray<Object> zero_length_array = new SparseArray<>(0);
    SparseArray<String> with_duplicates;

    @BeforeEach
    void setup() {
        array = new SparseArray<>(new int[] { 6, 11 }, new String[] { "Joe", "Léa" }, CUSTOM_LENGTH);
        with_duplicates = new SparseArray<>(
                new int[] { 2, 6, 7, 9 },
                new String[] { "Bob", "Alice", "Bob", "Bob" },
                10);
    }

    /***********************************************************************************
     Décommentez les tests question par question au fur et à mesure de votre avancement
     ***********************************************************************************/

    /*************************
     * Tests Question 4
     *************************/
    @Test
    void un_tableau_effacé_doit_être_vide() {
        assertFalse(array.isEmpty());
        assertEquals(2, array.size());
        array.clear();
        assertTrue(array.isEmpty());
        assertEquals(0, array.size());
    }

    @Test
    void un_tableau_effacé_ne_contient_aucune_valeur() {
        assert ("Joe".equals(array.get(6)));
        array.clear();
        for (int index = 0; index < 20; index++) {
            assertNull(array.get(index));
        }
    }

    @Test
    void le_tableau_initial_est_de_taille_2() {
        assertEquals(2, array.size());
    }

    @Test
    void un_tableau_vide_est_de_taille_0() {
        assertEquals(0, empty_array.size());
    }

    /*************************
     * Tests Question 5 get
     *************************/

    @Test
    void test_get() {
        assertNull(array.get(3));
        assertNull(array.get(0));
        assertEquals("Léa", array.get(11));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(CUSTOM_LENGTH));
    }

    @Test
    void test_get_value() {
        assertEquals("Joe", array.get(6));
        assertEquals("Léa", array.get(11));
        assertNull(array.get(3));
    }

    /*************************
     * Tests Question 6 (toString)
     *************************/
    @Test
    void test_SparseArray_toString() {
        SparseArray<Double> array_4 = new SparseArray(new int[] { 2, 3 }, new Double[] { 6.78, 3.1415 }, 4);
        assertEquals(new ArrayList().toString(), zero_length_array.toString());
        assertEquals(Arrays.asList(null, null, 6.78, 3.1415).toString(), array_4.toString());
    }

    /*************************
     * Tests Question 7 (indexOf)
     *************************/
    @Test
    void test_indexOf() {
        assertEquals(11, array.indexOf("Léa"));
        assertEquals(-1, array.indexOf("Unknown"));
        assertEquals(-1, zero_length_array.indexOf("Unknown"));
        assertEquals(2, with_duplicates.indexOf("Bob"));
        assertEquals(6, with_duplicates.indexOf("Alice"));
    }

    /*************************
     * Tests Question 8 (set)
     *************************/
    @Test
    void test_set_a_value() {
        assert array.get(8) == null;
        assert "Léa".equals(array.get(11));
        assertTrue(array.set(8, "Jack"));
        assertEquals("Jack", array.get(8));
        assertFalse(array.set(8, "Pauline"));
        assertEquals("Pauline", array.get(8));
        assertFalse(array.set(-1, "Paul"));
        assertFalse(array.set(CUSTOM_LENGTH, "Paul"));
        assertFalse(array.set(11, "Paul"));
    }

    /*************************
     * Tests Question 9 (remove(int))
     *************************/
    @Test
    void remove_un_element_existant_du_tableau() {
        assert array.get(8) == null;
        assert "Léa".equals(array.get(11));
        assertTrue(array.remove(11));
        assertFalse(array.remove(8));
    }

    @Test
    void remove_hors_limite() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(CUSTOM_LENGTH));
    }

    /*************************
     * Tests Question 10 (removeAll(Object))
     *************************/
    @Test
    void test_remove_all_object() {
        assert "Léa".equals(array.get(11));
        assertTrue(array.removeAll("Léa"));
        assertNull(array.get(11));

        assert "Bob" == with_duplicates.get(2);
        assert "Bob" == with_duplicates.get(7);
        assert "Bob" == with_duplicates.get(9);
        with_duplicates.removeAll("Bob");
        assertNull(with_duplicates.get(2));
        assertNull(with_duplicates.get(7));
        assertNull(with_duplicates.get(9));
        assertEquals("Alice", with_duplicates.get(6));
    }

    /*************************
     * Tests Question 11 (swap)
     *************************/
    @Test
    void teste_l_echange_de_valeurs_non_nulles() {
        assert "Léa".equals(array.get(11));
        assert "Joe".equals(array.get(6));
        array.swap(6, 11);
        assertEquals("Léa", array.get(6));
        assertEquals("Joe", array.get(11));
    }

    @Test
    void teste_l_echange_avec_un_ou_deux_index_hors_limite() {
        assert "Léa".equals(array.get(11));
        assert "Joe".equals(array.get(6));
        array.swap(6, 11);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.swap(-3, 6));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.swap(12, -6));
    }

    @Test
    void teste_l_echange_avec_une_valeur_nulle() {
        assert "Léa".equals(array.get(11));
        assert array.get(3) == null;
        array.swap(11, 3);
        assertEquals("Léa", array.get(3));
        assertNull(array.get(11));
        // Vérifie que null n'est pas rangé comme valeur
        assertTrue(array.set(11, "Ali"));
    }

    @Test
    void teste_l_echange_avec_deux_valeurs_nulles() {
        assert array.get(7) == null;
        assert array.get(0) == null;
        array.swap(0, 3);
        assertNull(array.get(3));
        assertNull(array.get(0));
        // Vérifie que null n'est pas rangé comme valeur
        assertTrue(array.set(0, "Ali"));
        assertTrue(array.set(3, "Ali"));
    }

    /*************************
     * Tests Question 12 (doublons)
     *************************/
    @Test
    void teste_doublons() {
        assertEquals(new HashSet<String>(), empty_array.doublons());
        assertEquals(new HashSet<String>(), zero_length_array.doublons());
        assertEquals(new HashSet<String>(Arrays.asList("Bob")), with_duplicates.doublons());
        SparseArray<String> with_many_duplicates = new SparseArray(
                new int[] { 2, 5, 6, 8, 9, 12 },
                new String[] { "Denis", "Katir", "Katir", "Chloé", "Katir", "Denis" },
                15);
        Set<String> duplicates = with_many_duplicates.doublons();
        assertEquals(2, duplicates.size());
        assertTrue(duplicates.contains("Katir"));
        assertTrue(duplicates.contains("Denis"));
    }

    /*************************
     * Tests Question 13 (Iterable)
     *************************/
    @Test
    void test_iteration_for_empty_array() {
        Iterator it = empty_array.iterator();
        assertTrue(it.hasNext());
    }
    
    @Test
    void test_iteration() {
        SparseArray<String> with_many_duplicates = new SparseArray(
                new int[] { 2, 5, 6, 8, 9, 12 },
                new String[] { "Denis", "Katir", "Katir", "Chloé", "Katir", "Denis" },
                15);
        Iterator<String> iterator = with_many_duplicates.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(null, iterator.next());
        assertEquals(null, iterator.next());
        assertEquals("Denis", iterator.next());
        assertEquals(null, iterator.next());
        assertEquals(null, iterator.next());
        assertEquals("Katir", iterator.next());
        assertEquals("Katir", iterator.next());
        assertEquals(null, iterator.next());
        assertEquals("Chloé", iterator.next());
        assertEquals("Katir", iterator.next());
        assertEquals(null, iterator.next());
        assertEquals(null, iterator.next());
        assertEquals("Denis", iterator.next());
        assertEquals(null, iterator.next());
        assertEquals(null, iterator.next());
        assertFalse(iterator.hasNext());
    }
}