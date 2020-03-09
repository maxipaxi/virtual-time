import java.util.*;

public class ScheduleMap<T> {

  private ArrayList<Long> _values;
  private ArrayList<T> _elements;
  private int _size;

  public ScheduleMap(){
    _values = new ArrayList<Long>();
    _elements = new ArrayList<T>();
  }

  public int size() {
    return _size;
  }

  public long firstKey() {
    return _values.get(0);
  }

  public Map.Entry<Long, T> pollFirstEntry(){
    _size--;
    swap(0, _size);
    bubbleDown(0);
    return new AbstractMap.SimpleEntry<>(_values.get(_size), _elements.get(_size));
  }
  
  public void put(long value, T element) {
    if (_values.size() <= _size) {
      _values.add(null);
      _elements.add(null);
    }
    _values.set(_size, value);
    _elements.set(_size, element);
    bubbleUp(_size);
    _size++;
  }

  private void swap(int a, int b){
    {
      var tmp = _values.get(a);
      _values.set(a, _values.get(b));
      _values.set(b, tmp);
    }
    {
      var tmp = _elements.get(a);
      _elements.set(a, _elements.get(b));
      _elements.set(b, tmp);
    }
  }

  private int parent(int index){
    return (index - 1) / 2;
  }

  private int left(int index){
    return 2 * index + 1;
  }

  private int right(int index){
    return 2 * index + 2;
  }

  private void bubbleUp(int index){
    if (index == 0)
      return;
    var p = parent(index);
    if (_values.get(index) < _values.get(p)){
      swap(index, p);
      bubbleUp(p);
    }
  }

  private void bubbleDown(int index){
    var l = left(index);
    var r = right(index);
    var lval = l < _size ? _values.get(l) : Long.MAX_VALUE;
    var rval = r < _size ? _values.get(r) : Long.MAX_VALUE;
    if (lval <= rval && lval < _values.get(index)) {
      swap(index, l);
      bubbleDown(l);
    } else if (rval < lval && rval < _values.get(index)) {
      swap(index, r);
      bubbleDown(r);
    }
  }

}