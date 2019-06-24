package com.jgo.demos.data;

/**
 * Created by ke-oh on 2019/06/15.
 *
 */

public class Sample {
    public final String name;

    public Sample(String name) {
        this.name = name;
    }

    /*public Intent buildIntent(
            Context context, boolean preferExtensionDecoders, String abrAlgorithm) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(PlayerActivity.PREFER_EXTENSION_DECODERS_EXTRA, preferExtensionDecoders);
        intent.putExtra(PlayerActivity.ABR_ALGORITHM_EXTRA, abrAlgorithm);
        if (drmInfo != null) {
            drmInfo.updateIntent(intent);
        }
        return intent;
    }*/

}
