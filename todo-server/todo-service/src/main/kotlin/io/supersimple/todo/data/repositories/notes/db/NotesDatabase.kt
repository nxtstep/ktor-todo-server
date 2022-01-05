package io.supersimple.todo.data.repositories.notes.db

import io.supersimple.db.SQLDatabase

class NotesDatabase(db: SQLDatabase) : SQLDatabase by db
