package io.supersimple.todo.routes.notes

import io.ktor.application.Application
import io.ktor.routing.Route
import io.supersimple.todo.features.notes.AddNoteUseCase
import io.supersimple.todo.features.notes.ListPrivateNotesUseCase
import io.supersimple.todo.features.notes.ListPublicNotesUseCase
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.controller.DIController
import org.kodein.di.subDI

class NotesController(application: Application) : DIController {
    private val addNoteUseCase: AddNoteUseCase by instance()
    private val listPrivateNotes: ListPrivateNotesUseCase by instance()
    private val listPublicNotes: ListPublicNotesUseCase by instance()

    override val di: DI by subDI(closestDI { application }) {
        import(notesModule)
    }

    override fun Route.getRoutes() {
        addNoteRoute(addNoteUseCase)
        listNotesRoute(listPublicNotes, listPrivateNotes)
    }
}
