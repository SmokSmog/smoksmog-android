package com.antyzero.smoksmog.ui.utils;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public abstract class ForwardingList<T> implements List<T> {

    protected abstract List<T> delegate();

    @Override
    public void add( int location, T object ) {
        delegate().add( location, object );
    }

    @Override
    public boolean add( T object ) {
        return delegate().add( object );
    }

    @Override
    public boolean addAll( int location, @NonNull Collection<? extends T> collection ) {
        return delegate().addAll( location, collection );
    }

    @Override
    public boolean addAll( @NonNull Collection<? extends T> collection ) {
        return delegate().addAll( collection );
    }

    @Override
    public void clear() {
        delegate().clear();
    }

    @Override
    public boolean contains( Object object ) {
        return delegate().contains( object );
    }

    @Override
    public boolean containsAll( @NonNull Collection<?> collection ) {
        return delegate().containsAll( collection );
    }

    @Override
    public boolean equals( Object object ) {
        return delegate().equals( object );
    }

    @Override
    public T get( int location ) {
        return delegate().get( location );
    }

    @Override
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override
    public int indexOf( Object object ) {
        return delegate().indexOf( object );
    }

    @Override
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return delegate().iterator();
    }

    @Override
    public int lastIndexOf( Object object ) {
        return delegate().lastIndexOf( object );
    }

    @Override
    public ListIterator<T> listIterator() {
        return delegate().listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator( int location ) {
        return delegate().listIterator( location );
    }

    @Override
    public T remove( int location ) {
        return delegate().remove( location );
    }

    @Override
    public boolean remove( Object object ) {
        return delegate().remove( object );
    }

    @Override
    public boolean removeAll( @NonNull Collection<?> collection ) {
        return delegate().removeAll( collection );
    }

    @Override
    public boolean retainAll( @NonNull Collection<?> collection ) {
        return delegate().retainAll( collection );
    }

    @Override
    public T set( int location, T object ) {
        return delegate().set( location, object );
    }

    @Override
    public int size() {
        return delegate().size();
    }

    @NonNull
    @Override
    public List<T> subList( int start, int end ) {
        return delegate().subList( start, end );
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return delegate().toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray( T1[] array ) {
        return delegate().toArray( array );
    }
}
