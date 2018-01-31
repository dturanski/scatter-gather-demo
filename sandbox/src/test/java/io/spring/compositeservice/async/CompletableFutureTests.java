package io.spring.compositeservice.async;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author David Turanski
 **/
public class CompletableFutureTests {

	private static Logger logger = LoggerFactory.getLogger(CompletableFutureTests.class);

	@Test
	public void testCompletedFuture() throws Exception {
		String expectedValue = "the expected value";
		CompletableFuture<String> alreadyCompleted = CompletableFuture.completedFuture(expectedValue);
		assertThat(alreadyCompleted.get()).isEqualTo(expectedValue);
	}

	@Test
	public void testRunAsync() throws Exception {

		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			logger.info("running this async");
		});

		future.get();
		assertThat(future.isDone());
	}

	@Test
	public void testSupplyAsync() throws Exception {

		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying this async");
			return "expected result";
		});

		assertThat(future.get()).isEqualTo("expected result");
	}

	@Test
	public void testAllOf() throws Exception {
		String expectedResult1 = "result1";
		Integer expectedResult2 = 5;
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying {}", expectedResult1);
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return expectedResult1;
		});
		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying {}", expectedResult2);
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return expectedResult2;
		});

		CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2);

		long start = new Date().getTime();

		all.get();

		long finish = new Date().getTime();

		assertThat(future1.get()).isEqualTo(expectedResult1);
		assertThat(future2.get()).isEqualTo(expectedResult2);
		assertThat(finish - start).isCloseTo(200, Offset.offset(10L));
	}

	@Test
	public void testAggregator() throws Exception {

		Foo foo = new Foo();

		CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying a Bar instance");
			return new Bar("bar");
		}).thenAccept(b -> {
			logger.info("got bar = {}" + b.val);
			foo.bar = b;
		});

		CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying a Baz instance");
			return new Baz("baz");
		}).thenAccept(b -> {
			logger.info("got baz = {}" + b.val);
			foo.baz = b;
		});

		CompletableFuture.allOf(future1, future2).get();

		assertThat(foo.bar).isNotNull();
		assertThat(foo.baz).isNotNull();
		assertThat(foo.bar.val).isEqualTo("bar");
		assertThat(foo.baz.val).isEqualTo("baz");
	}

	@Test
	public void testAggregatorException() throws Exception {

		Foo foo = new Foo();

		CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying a Bar instance");
			return new Bar("bar");
		}).thenAccept(b -> {
			logger.info("got bar = {}" + b.val);
			foo.bar = b;
		});

		CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> {
			logger.info("supplying a Baz instance");
			if (true) {
				IllegalArgumentException e = new IllegalArgumentException("invalid value");
				logger.error(e.getMessage());
				throw e;
			}
			return new Baz("baz");
		}).thenAcceptAsync(b -> {
			logger.info("got baz = {}" + b.val);
			foo.baz = b;
		});



		CompletableFuture all = CompletableFuture.allOf(future1, future2);
		try {
			all.get();
			fail("should throw and exception");

		} catch (ExecutionException e) {
			logger.error("got exception {}", e);

			assertThat(all.isDone()).isTrue();
			assertThat(all.isCompletedExceptionally()).isTrue();
			assertThat(future1.isDone()).isTrue();
			assertThat(future1.isCompletedExceptionally()).isFalse();
			assertThat(future2.isDone()).isTrue();
			assertThat(future2.isCompletedExceptionally()).isTrue();
		}
	}


	@Test
	public void testScatterGather() throws ExecutionException, InterruptedException {
		Foo foo = new Foo();
		ScatterGatherImpl.Step<Bar> step1 = new ScatterGatherImpl.Step(()-> {return new Bar("bar");},
			b->{foo.bar=(Bar)b;});
		ScatterGatherImpl.Step<Baz> step2 = new ScatterGatherImpl.Step(()-> {return new Baz("baz");},
			b->{foo.baz=(Baz)b;});

		CompletableFuture<Void> all = ScatterGatherImpl.builder().withSteps(step1,step2).build().result();

		all.get();
		assertThat(foo.bar).isNotNull();
		assertThat(foo.baz).isNotNull();
		assertThat(foo.bar.val).isEqualTo("bar");
		assertThat(foo.baz.val).isEqualTo("baz");


	}

	//
	static class Foo {
		Bar bar;
		Baz baz;
	}

	static class Bar {
		final String val;

		public Bar(String val) {
			this.val = val;
		}
	}

	static class Baz {
		final String val;

		public Baz(String val) {
			this.val = val;
		}
	}
}

