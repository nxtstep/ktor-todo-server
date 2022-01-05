package io.supersimple.todo.routes.notes

import io.supersimple.todo.data.repositories.notes.NotesRepository
import io.supersimple.todo.data.repositories.notes.db.NotesDatabaseRepository
import io.supersimple.todo.features.notes.AddNoteUseCase
import io.supersimple.todo.features.notes.ListPrivateNotesUseCase
import io.supersimple.todo.features.notes.ListPublicNotesUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val notesModule = DI.Module("notes-module") {
    bind<NotesRepository>() with singleton { NotesDatabaseRepository(instance()) }

    bind<AddNoteUseCase>() with provider { AddNoteUseCase(instance()) }
    bind<ListPrivateNotesUseCase>() with provider { ListPrivateNotesUseCase(instance()) }
    bind<ListPublicNotesUseCase>() with provider { ListPublicNotesUseCase(instance(), instance()) }
}
