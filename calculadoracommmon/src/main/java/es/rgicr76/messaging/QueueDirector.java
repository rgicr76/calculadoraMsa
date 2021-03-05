package es.rgicr76.messaging;

import java.util.function.BiFunction;

public class QueueDirector {
	public static <A1, A2 ,R> R process(A1 a1, A2 a2, BiFunction<A1, A2, R> func) {

		return func.apply(a1, a2);

	}

	public enum Queue {

		ADD("sumabackend:queue"), SUB("restabackend:queue"), MUL("multibackend:queue"), DIV("dividbackend:queue"), RES("calculadoraservice:queue");

		private String queue;

		private Queue(String queue) {
			this.queue = queue;
		}

		public String getQueue() {
			return queue;
		}

		public static Queue valueOfQueue(String label) {
			for (Queue e : values()) {
				if (e.queue.equals(label)) {
					return e;
				}
			}
			return null;
		}
	}
}
