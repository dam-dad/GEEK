//package dad.geek.model;
//
//import java.net.URL;
//
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleObjectProperty;
//
//public class Image {
//	
//	private IntegerProperty imageID = new SimpleIntegerProperty();
//	private ObjectProperty<URL> imageURL = new SimpleObjectProperty<>();
//	
//	public final int getImageID() {
//		return this.imageID.get();
//	}
//	
//	public final ObjectProperty<URL> imageURLProperty() {
//		return this.imageURL;
//	}
//	
//	public final URL getImageURL() {
//		return this.imageURLProperty().get();
//	}
//	
//	public final void setImageURL(final URL postImageURL) {
//		this.imageURLProperty().set(postImageURL);
//	}
//	
//}
