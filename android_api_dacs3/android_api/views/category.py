from flask import request, jsonify
from android_api.controllers.category import create_category, show_by_id_category, show_all_category,delete_category,update_category
from flask_cors import cross_origin
from flask import Blueprint

category = Blueprint("category", __name__)


@category.route("/api/category/create", methods=["POST"])
@cross_origin()
def category_create():
    if request.method != "POST":
        return jsonify({"msg": "add category fail", "error": "bad request"}, 401)
    else:
        add_status = create_category(
            request.form.get("categoryName"),
            request.form.get("categoryImage")
        )
    if add_status:
        return jsonify({"msg": "ok"}), 200
    else:
        return jsonify({"msg": "no create category", "data": None}, 401)


@category.route("/api/category/read_all", methods=["GET"])
@cross_origin()
def category_readAll():
    category = show_all_category()
    if category == None:
        return jsonify({"msg": "no category", "data": None}, 500)
    else:
        return jsonify(category), 200


@category.route("/api/category/read_by_id/<int:id_category>", methods=["GET"])
@cross_origin()
def category_readByID(id_category):
    category = show_by_id_category(id_category)
    if category == None:
        return jsonify({"msg": "no category", "data": None}, 500)
    else:
        return jsonify(category), 200


@category.route("/api/category/update/<int:id_user>", methods=["PUT"])
@cross_origin()
def category_update(id_category):
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "update category fail", "error": "bad request"}, 401)
        update_status = update_category(
            id_category,
            categoryName=res.get("categoryName"),
            categoryImage=res.get("categoryImage"))
    if update_status:
        return jsonify({"msg": "update category success", "data": res}, 200)
    else:
        return jsonify({"msg": "no update", "data": None}, 401)


@category.route("/api/category/delete/<int:id_category>", methods=["DELETE"])
@cross_origin()
def category_delete(id_category):
    category = delete_category(id_category)
    if category == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": category}, 200)
