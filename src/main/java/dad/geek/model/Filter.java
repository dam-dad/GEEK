package dad.geek.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Filter {

	private LongProperty filterID = new SimpleLongProperty();
	private StringProperty filterName = new SimpleStringProperty();
	private StringProperty filterShortName = new SimpleStringProperty();
	private StringProperty filterDescription = new SimpleStringProperty();

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

}
