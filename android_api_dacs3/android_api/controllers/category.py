from android_api.models import Category
from android_api import db


def create_category(categoryName, categoryImage):
    try:
        category = Category(categoryName, categoryImage)
        db.session.add(category)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_category():
    try:
        category = Category.query.all()
        data = []
        for item in category:
            data.append({
                "id_category": item.id_category,
                "categoryName": item.categoryName,
                "categoryImage": item.categoryImage
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_category(id_category):
    try:
        category = Category.query.filter_by(id_category = id_category)
        data = []
        for item in category:
            data.append({
                "id_category": item.id_category,
                "categoryName": item.categoryName,
                "categoryImage": item.categoryImage
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_category(id_category, categoryName, categoryImage):
    try:
        category = Category.query.filter_by(id_category = id_category).first()
        category.categoryName = categoryName
        category.categoryImage = categoryImage
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_category(id_category):
    try:
        category = Category.query.get(id_category)
        db.session.delete(category)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


