from flask import request, jsonify
from android_api_dacs3.android_api.controllers.category_book import delete_category_book, update_category_book, \
    create_category_book, show_all_category_book, show_by_id_category_book, show_by_id_book
from flask_cors import cross_origin
from flask import Blueprint

category_book = Blueprint("category_book", __name__)


@category_book.route("/api/category_book/create", methods=["POST"])
@cross_origin()
def category_book_create():
    if request.method != "POST":
        return jsonify({"msg": "add category fail", "error": "bad request"}, 401)
    else:
        add_status = create_category_book(
            request.form.get("id_category"),
            request.form.get("id_book")
        )
    if add_status:
        return jsonify({"msg": "ok"}), 200
    else:
        return jsonify({"msg": "no create category", "data": None}, 401)


@category_book.route("/api/category_book/read_all", methods=["GET"])
@cross_origin()
def category_book_readAll():
    category_book = show_all_category_book()
    if category_book == None:
        return jsonify({"msg": "no category_book", "data": None}, 500)
    else:
        return jsonify(category_book), 200


@category_book.route("/api/category_book/read_by_id/<int:id_category_book>", methods=["GET"])
@cross_origin()
def category_book_readByID(id_category_book):
    category_book = show_by_id_category_book(id_category_book)
    if category_book == None:
        return jsonify({"msg": "no category_book", "data": None}, 500)
    else:
        return jsonify(category_book), 200


@category_book.route("/api/category_book/read_by_id_book/<int:id_book>", methods=["GET"])
@cross_origin()
def category_book_readByID_book(id_book):
    category_book = show_by_id_book(id_book)
    if category_book == None:
        return jsonify({"msg": "no category_book", "data": None}, 500)
    else:
        return jsonify(category_book), 200


@category_book.route("/api/category_book/update/<int:id_category_book>", methods=["PUT"])
@cross_origin()
def category_book_update(id_category_book):
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "update category_book fail", "error": "bad request"}, 401)
        update_status = update_category_book(
            id_category_book,
            id_category=res.get("id_category"),
            id_book=res.get("id_book"))
    if update_status:
        return jsonify({"msg": "update category_book success", "data": res}, 200)
    else:
        return jsonify({"msg": "no update", "data": None}, 401)


@category_book.route("/api/category_book/delete/<int:id_category>/<int:id_book>", methods=["DELETE"])
@cross_origin()
def category_book_delete(id_category, id_book):
    category_book = delete_category_book(id_category, id_book)
    if category_book != None or category_book != False:
        return jsonify({"msg": "delete success", "data": category_book}, 200)
    else:
        return jsonify({"msg": "no update", "data": None}, 401)
