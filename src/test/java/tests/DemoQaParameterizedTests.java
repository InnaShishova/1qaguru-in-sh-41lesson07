package tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaParameterizedTests extends TestBase {

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    @ValueSource(strings = {
            "What is Lorem Ipsum?",
            "Where does it come from?",
            "Why do we use it?"
    })
    @ParameterizedTest(name = "Проверка вопроса в Accordian: {0}")
    void accordianShouldHaveQuestionText(String questionText) {
        open("/accordian");

        $(".accordian-container").shouldHave(text(questionText));
    }

    @CsvSource(value = {
            "Elements, Text Box",
            "Forms, Practice Form",
            "Widgets, Accordian"
    })
    @ParameterizedTest(name = "В категории {0} есть пункт меню {1}")
    void categoryShouldHaveMenuItem(String categoryName, String menuItemName) {
        open("/");

        $(".category-cards").shouldHave(text(categoryName));
        $(".category-cards").$(com.codeborne.selenide.Selectors.byText(categoryName)).click();

        $(".left-pannel").shouldHave(text(menuItemName));
    }

    static Stream<String> bookStoreSearchData() {
        return Stream.of(
                "Git",
                "JavaScript",
                "Design"
        );
    }

    @MethodSource("bookStoreSearchData")
    @ParameterizedTest(name = "Поиск книги по слову: {0}")
    void bookStoreShouldShowSearchResults(String searchValue) {
        open("/books");

        $("#searchBox").setValue(searchValue);

        $(".books-wrapper").shouldHave(text(searchValue));
    }
}