package com.andb.apps.aspen

import com.andb.apps.aspen.models.Subject
import com.andb.apps.aspen.models.SubjectGrade
import com.andb.apps.aspen.models.Term
import com.andb.apps.aspen.state.combineWith
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest : BaseTest() {

    @Test
    fun subjectListCombineTest() {
        assertEquals(thirdTerm.combineWith(fourthTerm), final) // test when adding smaller class list to larger one
        assertEquals(fourthTerm.combineWith(thirdTerm), final) // test when adding larger class list to smaller one
    }
}

private val thirdTerm = listOf(
    Subject("a", "A", "Mr. A", Subject.Config("a", Subject.Icon.ART, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.WithGrades(
                3,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            ),
            Term.Loading(4)
        )
    ),
    Subject("b", "B", "Mr. B", Subject.Config("b", Subject.Icon.ART, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.WithGrades(
                3,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            ),
            Term.Loading(4)
        )
    ),
    Subject("c", "C", "Mr. C", Subject.Config("c", Subject.Icon.CALCULUS, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.WithGrades(
                3,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            ),
            Term.Loading(4)
        )
    )
)

private val fourthTerm = listOf(
    Subject("a", "A", "Mr. A", Subject.Config("a", Subject.Icon.ART, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.Loading(3),
            Term.WithGrades(
                4,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            )
        )
    ),
    Subject("c", "C", "Mr. C", Subject.Config("c", Subject.Icon.CALCULUS, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.Loading(3),
            Term.WithGrades(
                4,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            )
        )
    )
)

private val final = listOf(
    Subject("a", "A", "Mr. A", Subject.Config("a", Subject.Icon.ART, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.WithGrades(
                3,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            ),
            Term.WithGrades(
                4,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            )
        )
    ),
    Subject("b", "B", "Mr. B", Subject.Config("b", Subject.Icon.ART, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.WithGrades(
                3,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            ),
            Term.Loading(4)
        )
    ),
    Subject("c", "C", "Mr. C", Subject.Config("c", Subject.Icon.CALCULUS, 0),
        listOf(
            Term.Loading(1),
            Term.Loading(2),
            Term.WithGrades(
                3,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            ),
            Term.WithGrades(
                4,
                listOf(),
                SubjectGrade.Letter(93.2, "A"),
                listOf()
            )
        )
    )
)