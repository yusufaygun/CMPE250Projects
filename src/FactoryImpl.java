import java.util.NoSuchElementException;

public class FactoryImpl implements Factory {
	
	private Holder first;
	private Holder last;
	private Integer size = 0;
	
		
	
	public void addFirst(Product product) {
		
		
		
		if (this.first == null) {
			first = new Holder(null, product, null);
			last = first;
			size++;
		}
		else {
			Holder holder = new Holder(null, product, first);
			first.setPreviousHolder(holder);
			first = holder;
			size++;
		}
	}
	
	
	public void addLast(Product product) {
		
		if(size == 0) {
			addFirst(product);
		}
		else {
			Holder holder = new Holder(last, product, null);
			last.setNextHolder(holder);
			last = holder;
			size++;
		}
	}
	
	public Product removeFirst() throws NoSuchElementException {
		
		if(size == 0) {
			throw new NoSuchElementException("Factory is empty.");
		}
		else {
			
			if(size == 1) {
				Product oldFirst = first.getProduct();
				first = null;
				size--;
				return oldFirst;
			}
			else {
				Product oldFirst = first.getProduct();
				first = first.getNextHolder();
				first.setPreviousHolder(null);
				size--;
				return oldFirst;
			}
		}
	}

	public Product removeLast() throws NoSuchElementException {
		if(size == 0) {
			throw new NoSuchElementException("Factory is empty.");
		}
		else {
			if(size == 1) {
				Product oldLast = first.getProduct();
				first = null;
				size--;
				return oldLast;
			}
			else {
				Product oldLast = last.getProduct();
				last.getPreviousHolder().setNextHolder(null);
				last = last.getPreviousHolder();
				size--;
			return oldLast;
			}
		}
	}
	
	public Product find(int id) throws NoSuchElementException {
		
		if (size == 0) {
			throw new NoSuchElementException("Product not found.");
		}
		
		Holder current = first;
		
		while (current.getProduct().getId() != id) {
			if (current == last) {
				throw new NoSuchElementException("Product not found.");
			}
			else {
				current = current.getNextHolder();
			}
		}
		
		return current.getProduct();
	}
	
	public Product update(int id, Integer value) throws NoSuchElementException {
		
		if (size == 0) {
			throw new NoSuchElementException("Product not found.");
		}
		
		Holder current = first;
		
		while (current.getProduct().getId() != id) {
			if (current == last) {
				throw new NoSuchElementException("Product not found.");
			}
			else {
				current = current.getNextHolder();
			}
		}
		
		Product oldProduct = new Product(current.getProduct().getId(), current.getProduct().getValue()); 
		current.getProduct().setValue(value);
		return oldProduct;
	}
	
	public Product get(int index) throws IndexOutOfBoundsException {
		
		if ( (index > size -1) || (index < 0) ) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		else {
			int currentIndex = 0;
			Holder current = first;
		
			while (currentIndex != index) {
				currentIndex++;
				current = current.getNextHolder();
			}
			
			return current.getProduct();
		}
	}
	
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		
		if ( (index > size) || (index < 0) ) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		else {
		
			if(index == 0) {
				addFirst(product);
				return;
			}
			
			if(index == size) {
				addLast(product);
				return;
			}
			
			int currentIndex = 0;
			Holder current = first;
		
		
			while (currentIndex != index) {
				current = current.getNextHolder();
				currentIndex++;
			}
		
			Holder prev = current.getPreviousHolder();
			Holder next = current;
			Holder newHolder = new Holder(prev, product, next);
		
			prev.setNextHolder(newHolder);
			next.setPreviousHolder(newHolder);
			size++;
		}
	}
	
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		
		if ( (index > size -1) || (index < 0) ) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
			
		}
		else {
			
			if (index == 0) {
				return removeFirst();
			}
			
			if (index == size - 1) {
				return removeLast();
			}
		
			int currentIndex = 0;
			Holder current = first;
			
		
			while(currentIndex != index) {
				currentIndex++;
				current = current.getNextHolder();
			}
		
			Holder prev = current.getPreviousHolder();
			Holder next = current.getNextHolder();
		
			prev.setNextHolder(next);
			next.setPreviousHolder(prev);
			size--;
		
			return current.getProduct();
		}
	}
	
	public Product removeProduct(int value) throws NoSuchElementException {
		
		if(size == 0) {
			throw new NoSuchElementException("Product not found.");
		}
		
		Holder current = first;
		
		while (current.getProduct().getValue() != value) {
			if (current == last) {
				throw new NoSuchElementException("Product not found.");
			}
			else {
				current = current.getNextHolder();
			}
		}
		if (current == first) {
			return removeFirst();
		}
		
		if (current == last) {
			return removeLast();
		}
		
		Holder prev = current.getPreviousHolder();
		Holder next = current.getNextHolder();
		
		prev.setNextHolder(next);
		next.setPreviousHolder(prev);
		size--;
		
		return current.getProduct();
	}
	
	public int filterDuplicates() {
		
		if (size == 0) {
			return 0;
		}
		
		Holder current = first;
		int currentIndex = 0;
		int count = 0;
		
		
		while(current != last) {
			for (int i = currentIndex + 1; i < size; i++) {
				if (current.getProduct().getValue() == get(i).getValue()) {
					removeIndex(i);
					i--;
					count++;
				}
				else {
					continue;	
				}
			}	
			
			if (current != last) {
				current = current.getNextHolder();
				currentIndex++;
			}
			else {
				continue;
			}
		}
		return count;
	}
	
	public void reverse() {
		
		if ((size == 0) || (size == 1)) {
			return;
		}
		
		else {
			
			Holder current = first;
			Holder next = first.getNextHolder();
			last = first;
			
			current.setNextHolder(null);
			current.setPreviousHolder(next);
			
			while(next != null) {
				
				next.setPreviousHolder(next.getNextHolder());
				next.setNextHolder(current);
				current = next;
				next = next.getPreviousHolder();
				
			}
			
			first = current;
			return;
			
		}	
	}
	
	public String toString() {
		String output = "{";
		Holder current = first;
		
		if (size == 0) {
			return output + "}";
		}
		
		while(current != last) {
			output += current.getProduct() +",";
			current = current.getNextHolder();
		} 
		
		if (current == null) {
			return  output + "}";
		}
		output += current.getProduct();
		return output + "}" ;
			
	}	
}