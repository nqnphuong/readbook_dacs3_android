from android_api_dacs3.android_api.models import Comment
from android_api_dacs3.android_api import db


def create_comment(id_user, id_book, comment):
    try:
        comment = Comment(id_user, id_book, comment)
        db.session.add(comment)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def show_all_comment():
    try:
        comment = Comment.query.all()
        data = []
        for item in comment:
            data.append({
                "id_comment": item.id_comment,
                "id_user": item.id_user,
                "id_book": item.id_book,
                "comment": item.comment
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_comment(id_comment):
    try:
        comment = Comment.query.filter_by(id_comment = id_comment)
        data = []
        for item in comment:
            data.append({
                "id_comment": item.id_comment,
                "id_user": item.id_user,
                "id_book": item.id_book,
                "comment": item.comment
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_comment(id_comment, id_user, id_book, comment):
    try:
        comment = Comment.query.filter_by(id_comment = id_comment).first()
        comment.commentName = id_user
        comment.commentImage = id_book
        comment.comment = comment
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_comment(id_comment):
    try:
        comment = Comment.query.get(id_comment)
        db.session.delete(comment)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False
