package nz.co.warehouseandroidtest.ui.barscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import nz.co.warehouseandroidtest.R;
import nz.co.warehouseandroidtest.ui.product.ProductDetailActivity;

public class BarScanActivity extends AppCompatActivity {

    private CaptureFragment captureFragment = new CaptureFragment();

    private boolean isOpen = false;

    private ImageView ivFlashToggle;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_bar_scan);

        ivFlashToggle = (ImageView) findViewById(R.id.iv_flashlight);
        ivFlashToggle.setImageDrawable(getDrawable(R.mipmap.ic_flash_off_black_24dp));
        ivFlashToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashlight();
            }
        });

        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_zxing_scan);

        captureFragment.setAnalyzeCallback(analyzeCallback);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
    }

    private CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent intent = new Intent();
            intent.setClass(BarScanActivity.this, ProductDetailActivity.class);
            intent.putExtra(ProductDetailActivity.FLAG_INTENT_SOURCE, ProductDetailActivity.FLAG_INTENT_SOURCE_SCAN);
            intent.putExtra(ProductDetailActivity.FLAG_BAR_CODE, result);
            startActivity(intent);
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(BarScanActivity.this, "Oops, bar code analysis failed!", Toast.LENGTH_SHORT);
            finish();
        }
    };

    void toggleFlashlight() {
        if (!isOpen) {
            CodeUtils.isLightEnable(true);
            ivFlashToggle.setImageDrawable(getDrawable(R.mipmap.ic_flash_on_black_24dp));
            isOpen = true;
        } else {
            CodeUtils.isLightEnable(false);
            ivFlashToggle.setImageDrawable(getDrawable(R.mipmap.ic_flash_off_black_24dp));
            isOpen = false;
        }
    }

}
