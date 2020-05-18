import java.util.*;

public class ScheduleMap<T> {

  public static class LongEntry<T> {
    private long _key;
    private T _value;
    private LongEntry(long key, T value) {
      _key = key;
      _value = value;
    }
    public long getKey() { return _key; }
    public T getValue() { return _value; }
  }

  private ArrayList<LongEntry<T>> _elements;
  private int _size;

  public ScheduleMap() {
    _elements = new ArrayList<LongEntry<T>>();
  }

  public int size() {
    return _size;
  }

  public long firstKey() {
    return _elements.get(0).getKey();
  }

  public LongEntry<T> pollFirstEntry() {
    _size--;
    swap(0, _size);
    bubbleDown(0);
    return _elements.get(_size);
  }
  
  public void put(long value, T element) {
    if (_elements.size() <= _size) {
      _elements.add(null);
    }
    _elements.set(_size, new LongEntry<T>(value, element));
    bubbleUp(_size);
    _size++;
  }

  private void swap(int a, int b) {
    var tmp = _elements.get(a);
    _elements.set(a, _elements.get(b));
    _elements.set(b, tmp);
  }

  private int parent(int index) {
    return (index - 1) / 2;
  }

  private int left(int index) {
    return 2 * index + 1;
  }

  private int right(int index) {
    return 2 * index + 2;
  }

  private void bubbleUp(int index) {
    if (index == 0)
      return;
    var p = parent(index);
    if (_elements.get(index).getKey() < _elements.get(p).getKey()) {
      swap(index, p);
      bubbleUp(p);
    }
  }

  private void bubbleDown(int index) {
    var l = left(index);
    var r = right(index);
    var lval = l < _size ? _elements.get(l).getKey() : Long.MAX_VALUE;
    var rval = r < _size ? _elements.get(r).getKey() : Long.MAX_VALUE;
    if (lval <= rval && lval < _elements.get(index).getKey()) {
      swap(index, l);
      bubbleDown(l);
    } else if (rval < lval && rval < _elements.get(index).getKey()) {
      swap(index, r);
      bubbleDown(r);
    }
  }

}