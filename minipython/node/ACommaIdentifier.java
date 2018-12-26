/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class ACommaIdentifier extends PCommaIdentifier
{
    private TId _id_;
    private final LinkedList _equalValue_ = new TypedLinkedList(new EqualValue_Cast());

    public ACommaIdentifier()
    {
    }

    public ACommaIdentifier(
        TId _id_,
        List _equalValue_)
    {
        setId(_id_);

        {
            this._equalValue_.clear();
            this._equalValue_.addAll(_equalValue_);
        }

    }
    public Object clone()
    {
        return new ACommaIdentifier(
            (TId) cloneNode(_id_),
            cloneList(_equalValue_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACommaIdentifier(this);
    }

    public TId getId()
    {
        return _id_;
    }

    public void setId(TId node)
    {
        if(_id_ != null)
        {
            _id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _id_ = node;
    }

    public LinkedList getEqualValue()
    {
        return _equalValue_;
    }

    public void setEqualValue(List list)
    {
        _equalValue_.clear();
        _equalValue_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_id_)
            + toString(_equalValue_);
    }

    void removeChild(Node child)
    {
        if(_id_ == child)
        {
            _id_ = null;
            return;
        }

        if(_equalValue_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        for(ListIterator i = _equalValue_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set(newChild);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

    }

    private class EqualValue_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PEqualValue node = (PEqualValue) o;

            if((node.parent() != null) &&
                (node.parent() != ACommaIdentifier.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != ACommaIdentifier.this))
            {
                node.parent(ACommaIdentifier.this);
            }

            return node;
        }
    }
}
