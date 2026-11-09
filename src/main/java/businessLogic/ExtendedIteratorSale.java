package businessLogic;

import java.util.List;

import domain.Sale;

public class ExtendedIteratorSale implements ExtendedIterator {

	private int pos;
	private List<Sale> listSale;
	
	public ExtendedIteratorSale (List<Sale> lista) {
		listSale=lista;
		this.pos=0;
	}
	@Override
	public boolean hasNext() {
		if(pos>=listSale.size()) return false;
		return true;
	}

	@Override
	public Object next() {
		Sale obj=listSale.get(pos);
		pos++;
		return obj;
	}

	@Override
	public Object previous() {
		pos--;
		Sale obj=listSale.get(pos);
		return obj;
	}

	@Override
	public boolean hasPrevious() {
		if(pos<=0) return false;
		return true;
	}

	@Override
	public void goFirst() {
		// TODO Auto-generated method stub
		pos=0;
	}

	@Override
	public void goLast() {
		// TODO Auto-generated method stub
		pos=listSale.size();
	}

}
