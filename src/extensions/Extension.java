package extensions;

public interface Extension {
	
	public void onEvent(services.Status e);
	
	public void loadExtension();
	
}
