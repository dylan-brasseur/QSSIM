package plugins.dbrasseur.qssim;

import icy.image.IcyBufferedImage;
import icy.image.IcyBufferedImageUtil;
import icy.sequence.Sequence;
import icy.type.DataType;
import plugins.adufour.ezplug.*;
import plugins.dbrasseur.qssim.quaternion.Quat;

import java.util.ArrayList;

/**
 * Implementation of "Quaternion Structural Similarity: A New Quality Index for Color Images" by Kolaman and Yadid
 * @author Dylan Brasseur
 * @version 1.0
 *
 */
public class QSSIMPlug extends EzPlug{

	private EzVarSequence	EzSrcSeq;				//Source Sequence
    private EzVarSequence   EzDegSeq;               //Degraded Sequence


	@Override
	public void clean() {
		// TODO Auto-generated by Icy4Eclipse
	}

	@Override
	protected void execute() {
        Sequence src = EzSrcSeq.getValue();
        Sequence deg = EzDegSeq.getValue();
		ArrayList<IcyBufferedImage> srcImages = src.getAllImage();
		ArrayList<IcyBufferedImage> degImages = deg.getAllImage();
        int maxindex = Math.min(srcImages.size(), degImages.size());
        for(int i=0; i<maxindex; i++)
		{
			IcyBufferedImage srcImg = IcyBufferedImageUtil.convertToType(srcImages.get(i), DataType.DOUBLE,true);
			IcyBufferedImage degImg = IcyBufferedImageUtil.convertToType(degImages.get(i), DataType.DOUBLE,true);
			double qssim = QSSIM.computeQSSIM(srcImg, degImg);
			System.out.println(qssim);
		}
	}

	@Override
	protected void initialize() {
		super.setTimeDisplay(true);
		EzSrcSeq = new EzVarSequence("Source sequence");
		EzDegSeq = new EzVarSequence("Degraded sequence");
		super.addEzComponent(EzSrcSeq);
		super.addEzComponent(EzDegSeq);
	}
}