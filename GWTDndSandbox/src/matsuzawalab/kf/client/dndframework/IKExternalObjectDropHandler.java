package matsuzawalab.kf.client.dndframework;


public interface IKExternalObjectDropHandler {

	public boolean isAcceptable(KFDataTransfer dataTransfer);

	public void dropped(KFDataTransfer dataTransfer, int x, int y);

}
