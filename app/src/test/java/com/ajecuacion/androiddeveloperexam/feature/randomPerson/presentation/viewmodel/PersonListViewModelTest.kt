package com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.*
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.viewmodels.PersonListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [28])
class PersonListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getPersonsUseCase: GetPersonListUseCase

    @Mock
    private lateinit var refreshPersonsUseCase: RefreshPersonsUseCase

    @Mock
    private lateinit var loadMorePersonsUseCase: LoadMorePersonsUseCase

    @Mock
    private lateinit var getPersonUseCase: GetPersonDetailsUseCase

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var useCases: UseCases
    private lateinit var viewModel: PersonListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        useCases = UseCases(
            getPersonsUseCase = getPersonsUseCase,
            refreshPersonsUseCase = refreshPersonsUseCase,
            loadMorePersonsUseCase = loadMorePersonsUseCase,
            getPersonDetailsUseCase = getPersonUseCase
        )
        viewModel = PersonListViewModel(useCases, savedStateHandle)

        // Mocking the use cases to return a non-null Flow
        val personList = listOf(
            Person(
                id = "2b5c04a0-ab48-4cc7-adbc-ee33d252c38d",
                username = "sadrabbit913",
                firstName = "Ascenso",
                lastName = "Nunes",
                city = "Curitiba",
                profilePicture = "https://randomuser.me/api/portraits/men/21.jpg",
                email = "ascenso.nunes@example.com",
                phone = "(81) 3172-5822",
                cell = "(64) 9353-2932",
                dob = "1995-11-22T05:38:55.218Z",
                age = 28,
                street = "7910 Avenida da Democracia",
                state = "Piauí",
                country = "Brazil",
                postcode = "13807",
                contactPerson = "John Doe",
                contactPersonPhone = "123-456-7890"
            )
        )
        val flow: Flow<Resource<List<Person>>> = flow { emit(Resource.Success(personList)) }

        // Using runTest for mocking suspend functions
        runTest {
            Mockito.`when`(getPersonsUseCase.invoke()).thenReturn(flow)
            Mockito.`when`(refreshPersonsUseCase.invoke()).thenReturn(flow)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getPersons is called, persons are fetched successfully`() = runTest {
        viewModel.getPersons()
        val value = viewModel.persons.value

        assert(value is Resource.Success)
        (value as Resource.Success).data?.let { assert(it.isNotEmpty()) }
    }

    @Test
    fun `when refreshPersons is called, persons are refreshed successfully`() = runTest {
        viewModel.refreshPersons()
        val value = viewModel.persons.value

        assert(value is Resource.Success)
        (value as Resource.Success).data?.let { assert(it.isNotEmpty()) }
    }
}
