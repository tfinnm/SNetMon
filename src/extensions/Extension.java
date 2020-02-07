package extensions;

import services.Service;

public interface Extension {
	
	public void onEvent(Service.status e);
	
	public void loadExtension();
	
}
