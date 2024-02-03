package di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import ui.booking.BookingViewModel

fun initKoin() =
    startKoin {
        modules(
            viewModels,
        )
    }

val viewModels = module {
    factory { BookingViewModel() }
}