package com.doublechaintech;

import java.util.*;
import java.util.function.UnaryOperator;

public class SmartList<T extends BaseEntity> extends BaseEntity implements Iterable<T> {
  private String name;
  private List<T> data = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String pName) {
    name = pName;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> pData) {
    data = pData;
  }

  public int size() {
    return data.size();
  }

  public boolean isEmpty() {
    return data.isEmpty();
  }

  public boolean contains(Object o) {
    return data.contains(o);
  }

  public Iterator<T> iterator() {
    return data.iterator();
  }

  public Object[] toArray() {
    return data.toArray();
  }

  public <T1> T1[] toArray(T1[] a) {
    return data.toArray(a);
  }

  public boolean add(T pT) {
    return data.add(pT);
  }

  public boolean remove(Object o) {
    return data.remove(o);
  }

  public boolean containsAll(Collection<?> c) {
    return data.containsAll(c);
  }

  public boolean addAll(Collection<? extends T> c) {
    return data.addAll(c);
  }

  public boolean addAll(int index, Collection<? extends T> c) {
    return data.addAll(index, c);
  }

  public boolean removeAll(Collection<?> c) {
    return data.removeAll(c);
  }

  public boolean retainAll(Collection<?> c) {
    return data.retainAll(c);
  }

  public void replaceAll(UnaryOperator<T> operator) {
    data.replaceAll(operator);
  }

  public void sort(Comparator<? super T> c) {
    data.sort(c);
  }

  public void clear() {
    data.clear();
  }

  public boolean equals(Object o) {
    return data.equals(o);
  }

  public int hashCode() {
    return data.hashCode();
  }

  public T get(int index) {
    return data.get(index);
  }

  public T set(int index, T element) {
    return data.set(index, element);
  }

  public void add(int index, T element) {
    data.add(index, element);
  }

  public T remove(int index) {
    return data.remove(index);
  }

  public int indexOf(Object o) {
    return data.indexOf(o);
  }

  public int lastIndexOf(Object o) {
    return data.lastIndexOf(o);
  }

  public ListIterator<T> listIterator() {
    return data.listIterator();
  }

  public ListIterator<T> listIterator(int index) {
    return data.listIterator(index);
  }

  public List<T> subList(int fromIndex, int toIndex) {
    return data.subList(fromIndex, toIndex);
  }

  public Spliterator<T> spliterator() {
    return data.spliterator();
  }

  public void deleteAllElements() {
    for (T datum : data) {
      datum.markAsDeleted();
    }
  }
}
