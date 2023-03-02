package dad.geek.model;

import dad.geek.App;
import dad.geek.db.DBManager;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase Filter, contiene todos los datos del filtro.
 *
 */
public class Filter {

	private LongProperty filterID = new SimpleLongProperty();
	private StringProperty filterName = new SimpleStringProperty();
	private StringProperty filterShortName = new SimpleStringProperty();
	private StringProperty filterDescription = new SimpleStringProperty();
	
	/**
	 * Constructor genérico de la clase {@link Filter}
	 */
	public Filter() {
	}
	
	/**
	 * Constructor de la clase {@link Filter}
	 * @param filterID
	 * @param filterName
	 * @param filterShortName
	 * @param filterDescription
	 */
	public Filter(long filterID, String filterName, String filterShortName, String filterDescription) {
		setFilterID(filterID);
		setFilterName(filterName);
		setFilterShortName(filterShortName);
		setFilterDescription(filterDescription);
	}
	
	/**
	 * Llama a la función {@link DBManager#createFilter(Filter)}.
	 * @throws Exception
	 */
	public void addFiltertoDB() throws Exception {
		App.conexionDB.createFilter(this);
	}

	public final LongProperty filterIDProperty() {
		return this.filterID;
	}

	public final long getFilterID() {
		return this.filterIDProperty().get();
	}

	public final void setFilterID(final long filterID) {
		this.filterIDProperty().set(filterID);
	}

	public final StringProperty filterNameProperty() {
		return this.filterName;
	}

	public final String getFilterName() {
		return this.filterNameProperty().get();
	}

	public final void setFilterName(final String filterName) {
		this.filterNameProperty().set(filterName);
	}

	public final StringProperty filterShortNameProperty() {
		return this.filterShortName;
	}

	public final String getFilterShortName() {
		return this.filterShortNameProperty().get();
	}

	public final void setFilterShortName(final String filterShortName) {
		this.filterShortNameProperty().set(filterShortName);
	}

	public final StringProperty filterDescriptionProperty() {
		return this.filterDescription;
	}

	public final String getFilterDescription() {
		return this.filterDescriptionProperty().get();
	}

	public final void setFilterDescription(final String filterDescription) {
		this.filterDescriptionProperty().set(filterDescription);
	}
	
	@Override
	public String toString() {
		return getFilterName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filter other = (Filter) obj;
		if (getFilterID() != other.getFilterID())
			return false;
		return true;
	}

}
