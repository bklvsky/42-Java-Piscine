import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

	private Integer size;

	private final Node head = new Node(null, null, null);
	private final Node tail = new Node (null, null, null);

	public TransactionsLinkedList() {
		this.size = 0;
		head.setNext(tail);
		tail.setPrev(head);
	}

	public void addTransaction(Transaction tr) {
		Node tmplast = tail.getPrev();
		Node newNode = new Node(tmplast, tail, tr);

		tmplast.setNext(newNode);
		tail.setPrev(newNode);
		this.size++;
	}

	public void removeTransaction(UUID id) {
		Node tmp = this.head.getNext();
		while (tmp != tail) {
			if (tmp.getTr().getIdentifier() == id) {
				tmp.getPrev().setNext(tmp.getNext());
				tmp.getNext().setPrev(tmp.getPrev());
				this.size--;
				break;
			}
			tmp = tmp.getNext();
		}
		if (tmp == tail) {
			throw new TransactionNotFoundException("ID[" + id + "] invalid");
		}
	}

	public Transaction[] toArray() {
		Transaction[] arr = new Transaction[this.size];
		Node tmp = this.head.getNext();

		for (int i = 0; i < this.size; i++) {
			arr[i] = tmp.getTr();
			tmp = tmp.getNext();
		}
		return arr;
	}

	private class Node {
		Node prev;
		Node next;
		Transaction tr;

		public Node(Node prev, Node next, Transaction tr) {
			this.setPrev(prev);
			this.setNext(next);
			this.setTr(tr);
		}
		public Node getPrev() {
			return prev;
		}
		public void setPrev(Node prev) {
			this.prev = prev;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		public Transaction getTr() {
			return tr;
		}
		public void setTr(Transaction tr) {
			this.tr = tr;
		}
	}
}
