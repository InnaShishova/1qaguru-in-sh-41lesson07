package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DemoQaParameterizedTests extends TestBase {

    @ValueSource(strings = {
            "What is Lorem Ipsum?",
            "Where does it come from?",
            "Why do we use it?"
    })
    //1
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

    //2
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
    //3
    @ParameterizedTest(name = "Поиск книги по слову: {0}")
    void bookStoreShouldShowSearchResults(String searchValue) {
        open("/books");

        $("#searchBox").setValue(searchValue);

        $(".rt-tbody").shouldHave(text(searchValue));
    }
}