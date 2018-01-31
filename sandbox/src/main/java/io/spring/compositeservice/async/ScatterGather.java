package io.spring.compositeservice.async;

/**
 * @author David Turanski
 **/
public abstract class ScatterGather<T> {
	protected  ScatterGather(Distributor<T> distributor, Aggregator aggregator) {

	}


}
