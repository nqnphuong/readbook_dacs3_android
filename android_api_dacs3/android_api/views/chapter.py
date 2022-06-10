from flask import request, jsonify
from android_api_dacs3.android_api.controllers.chapter import create_chapter, show_by_id_chapter, show_all_chapter, \
    delete_chapter, update_chapter, show_by_id_book
from flask_cors import cross_origin
from flask import Blueprint

chapter = Blueprint("chapter", __name__)


@chapter.route("/api/chapter/create", methods=["GET", "POST"])
@cross_origin()
def chapter_create():
    # for key, value in res.items():
        if request.method != "POST":
            return jsonify({"msg": "add chapter fail", "error": "bad request"}, 401)
        else:
            add_status = create_chapter(
                id_book=request.form.get("id_book"),
                chapterContent=request.form.get("chapterContent"),
                chapterNumberLove=request.form.get("chapterNumberLove"),
                chapterTitle=request.form.get("chapterTitle"))
        if add_status:
            return jsonify({"msg": "ok"}), 200
        else:
            return jsonify({"msg": "no chapter", "data": None}, 401)


@chapter.route("/api/chapter/read_all", methods=["GET"])
@cross_origin()
def chapter_readAll():
    chapter = show_all_chapter()
    if chapter == None:
        return jsonify({"msg": "no chapter", "data": None}, 500)
    else:
        return jsonify(chapter), 200


@chapter.route("/api/chapter/read_by_id/<int:id_chapter>", methods=["GET"])
@cross_origin()
def chapter_readByID(id_chapter):
    chapter = show_by_id_chapter(id_chapter)
    if chapter == None:
        return jsonify({"msg": "no chapter", "data": None}, 500)
    else:
        return jsonify(chapter), 200


@chapter.route("/api/chapter/read_by_id_book/<int:id_book>", methods=["GET"])
@cross_origin()
def chapter_readByIDbook(id_book):
    chapter = show_by_id_book(id_book)
    if chapter == None:
        return jsonify({"msg": "no chapter", "data": None}, 500)
    else:
        return jsonify(chapter), 200

@chapter.route("/api/chapter/update/<int:id_chapter>", methods=["PUT"])
@cross_origin()
def chapter_update(id_chapter):
    if request.method != "PUT":
        return jsonify({"msg": "update user fail", "error": "bad request"}, 401)
    else:
        update_status = update_chapter(
            id_chapter,
            chapterContent=request.form.get("chapterContent"),
            chapterTitle=request.form.get("chapterTitle"))
    if update_status:
        print(id_chapter)
        user = show_by_id_chapter(id_chapter)
        print(user)
        return jsonify(user), 200
    else:
        return jsonify({"msg": "no update user", "data": None}, 401)


@chapter.route("/api/chapter/delete/<int:id_chapter>", methods=["DELETE"])
@cross_origin()
def chapter_delete(id_chapter):
    chapter = delete_chapter(id_chapter)
    if chapter == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": chapter}, 200)
