from flask import request, jsonify
from android_api.controllers.library import create_library, show_by_id_library, show_all_library, \
    delete_library, update_library, show_by_id_library_id_user
from flask_cors import cross_origin
from flask import Blueprint

library = Blueprint("library", __name__)


@library.route("/api/library/create", methods=["POST"])
@cross_origin()
def library_create():
    if request.method != "POST":
        return jsonify({"msg": "add library fail", "error": "bad request"}, 401)
    else:
        add_status = create_library(
            id_user=request.form.get("id_user"),
            id_book=request.form.get("id_book"),
            libraryStatusread=request.form.get("libraryStatusread"))
    if add_status:
        return jsonify({"msg": "ok"}), 200
    else:
        return jsonify({"msg": "no library user", "data": None}, 401)



@library.route("/api/library/read_all", methods=["GET"])
@cross_origin()
def library_readAll():
    library = show_all_library()
    if library == None:
        return jsonify({"msg": "no library", "data": None}, 500)
    else:
        return jsonify(library), 200


@library.route("/api/library/read_by_id/<int:id_library>", methods=["GET"])
@cross_origin()
def library_readByID(id_library):
    library = show_by_id_library(id_library)
    if library == None:
        return jsonify({"msg": "no library", "data": None}, 500)
    else:
        return jsonify(library), 200


@library.route("/api/library/read_by_id_user_book/<int:id_book>/<int:id_user>", methods=["GET"])
@cross_origin()
def library_readByID_iduser(id_book, id_user):
    library = show_by_id_library_id_user(id_book, id_user)
    if library == None or not library:
        return jsonify({"msg": "no library"}, 500)
    else:
        return jsonify(library), 200


@library.route("/api/library/update/<int:id_library>", methods=["PUT"])
@cross_origin()
def library_update(id_library):
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "update library fail", "error": "bad request"}, 401)
        update_status = update_library(
            id_library,
            id_user=res.get("id_user"),
            id_book=res.get("id_book"),
            libraryStatusread=res.get("libraryStatusread"))
    if update_status:
        return jsonify({"msg": "update library success", "data": res}, 200)
    else:
        return jsonify({"msg": "no update", "data": None}, 401)


@library.route("/api/library/delete/<int:id_library>", methods=["DELETE"])
@cross_origin()
def library_delete(id_library):
    library = delete_library(id_library)
    if library == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": library}, 200)
