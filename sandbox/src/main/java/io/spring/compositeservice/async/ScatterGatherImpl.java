package io.spring.compositeservice.async;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author David Turanski
 **/
public class ScatterGatherImpl {
	private final CompletableFuture<Void>[] all;

	 private ScatterGatherImpl(Step[] steps) {

		all = new CompletableFuture[steps.length];

		for (int i =0; i < steps.length; i++) {
			all[i] = CompletableFuture.supplyAsync(steps[i].getSupplier())
			.thenAccept(steps[i].getConsumer());
		}
	}

	public  CompletableFuture<Void> result() {
	 	return CompletableFuture.allOf(all);
	}

	public static ScatterGatherBuilder builder() {
		return new ScatterGatherBuilder();
	}



	public static class ScatterGatherBuilder {
		private Step<?>[] steps;

		public ScatterGatherBuilder withSteps(Step<?>... steps) {
			this.steps = steps;
			return this;
		}



		public ScatterGatherImpl build() {
			return new ScatterGatherImpl(steps);
		}
	}

	public static class Step<T> {
		private final Supplier<T> supplier;
		private final Consumer<T> consumer;

		public Step(Supplier<T> supplier, Consumer<T> consumer) {
			this.supplier = supplier;
			this.consumer = consumer;
		}

		Supplier<T> getSupplier() {
			return supplier;
		}

		Consumer<T> getConsumer() {
			return consumer;
		}
	}
}
