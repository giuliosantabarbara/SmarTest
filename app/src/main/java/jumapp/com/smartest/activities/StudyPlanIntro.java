package jumapp.com.smartest.activities;


import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import jumapp.com.smartest.R;
import jumapp.com.smartest.fragments.CustomSelectionData;
import jumapp.com.smartest.fragments.CustomSlide;


public class StudyPlanIntro extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.third_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.student)
                        .title("Organizza il tuo studio con noi")
                        .description("Vuoi provare?")
                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.second_slide_background)
                .buttonsColor(R.color.second_slide_buttons)
                .title("Want more?")
                .description("Go on")
                .build());


        addSlide(new CustomSelectionData());
        addSlide(new CustomSlide());


        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.fourth_slide_background)
                .buttonsColor(R.color.fourth_slide_buttons)
                .title("That's it")
                .description("Would you join us?")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Toast.makeText(this, "Il tuo piano di studio è pronto! :)", Toast.LENGTH_SHORT).show();
    }
}