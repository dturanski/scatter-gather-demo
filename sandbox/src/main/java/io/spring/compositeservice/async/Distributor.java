package io.spring.compositeservice.async;

/**
 * @author David Turanski
 **/
public interface Distributor<T> {
	T distribute();

}
