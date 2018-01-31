package io.spring.compositeservice.async;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author David Turanski
 **/
public class AsyncDistributor implements Distributor<CompletableFuture<Object>> {

	private final CompletableFuture distributor;

	public AsyncDistributor(List<Supplier<?>> suppliers) {
		List<CompletableFuture> futures = new LinkedList<>();
		for (Supplier<?> supplier : suppliers) {
			futures.add(CompletableFuture.supplyAsync(supplier));
		}
		CompletableFuture[] all = new CompletableFuture[suppliers.size()];

		distributor = CompletableFuture.allOf(futures.toArray(all));
	}

	@Override
	public CompletableFuture<Object> distribute() {
		return distributor;
	}
}
