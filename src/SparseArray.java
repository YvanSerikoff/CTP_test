import java.util.*;

public class SparseArray<E> implements Iterable<E>{
    private final int DEFAULT_LENGTH = 30;

    private int length;
    private SortedMap<Integer, E> map;

    public SparseArray() {
        this.length = DEFAULT_LENGTH;
        this.map = new TreeMap<>();
    }

    public SparseArray(int length) {
        this.length = length;
        this.map = new TreeMap<>();
    }

    protected SparseArray(int[] tabInt, E[] tabE, int length){
        this(length);
        this.map = new TreeMap<>();
        for (int i = 0; i < tabInt.length; i++) {
            map.put(tabInt[i], tabE[i]);
        }
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public void clear(){
        map.clear();
    }

    public int size(){
        return map.size();
    }

    public E get(int index){
        if(index < 0 || index >= length){
            throw new ArrayIndexOutOfBoundsException();
        }
        return map.get(index) == null ? null : map.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(map.get(i) == null ? "null" : map.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public int indexOf(Object e){
        boolean found = false;
        int i = 0;
        while(i < length && !found){
            if(map.get(i) != null && map.get(i).equals(e)){
                found = true;
            }else{
                i++;
            }
        }
        return found ? i : -1;
    }

    public boolean set(int index, E value) {
        if (index < 0 || index >= length || value == null) {
            return false;
        }
        if (map.containsKey(index)) {
            map.put(index, value);
            return false;
        }
        map.put(index, value);
        return true;
    }

    public boolean remove(int index) {
        if (index < 0 || index >= length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return map.remove(index) != null;
    }

    public boolean removeAll(Object e){
        boolean found = false;
        for (int i = 0; i < length; i++) {
            if(map.get(i) != null && map.get(i).equals(e)){
                map.remove(i);
                found = true;
            }
        }
        return found;
    }

    public void swap(int i, int j) {
        if (i < 0 || i >= length || j < 0 || j >= length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E temp = map.get(i);
        if (map.containsKey(i)) {
            if (map.containsKey(j)) {
                map.put(i, map.get(j));
            } else {
                map.remove(i);
            }
        }
        if (temp != null) {
            map.put(j, temp);
        } else {
            map.remove(j);
        }
    }

    public Set<E> doublons(){
        Set<E> doublons = new HashSet<>();
        Set<E> unique = new HashSet<>();
        for (int i = 0; i < length; i++) {
            if(map.get(i) != null && !unique.add(map.get(i))){
                doublons.add(map.get(i));
            }
        }
        return doublons;
    }

    @Override
    public Iterator<E> iterator() {
        return new SparseArrayIterator();
    }

    private class SparseArrayIterator implements Iterator<E> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < length;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return map.get(currentIndex++);
        }
    }
}
