/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AAssertComparisonStatement extends PStatement
{
    private PComparison _comparison_;
    private final LinkedList _commaExpression_ = new TypedLinkedList(new CommaExpression_Cast());

    public AAssertComparisonStatement()
    {
    }

    public AAssertComparisonStatement(
        PComparison _comparison_,
        List _commaExpression_)
    {
        setComparison(_comparison_);

        {
            this._commaExpression_.clear();
            this._commaExpression_.addAll(_commaExpression_);
        }

    }
    public Object clone()
    {
        return new AAssertComparisonStatement(
            (PComparison) cloneNode(_comparison_),
            cloneList(_commaExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAssertComparisonStatement(this);
    }

    public PComparison getComparison()
    {
        return _comparison_;
    }

    public void setComparison(PComparison node)
    {
        if(_comparison_ != null)
        {
            _comparison_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _comparison_ = node;
    }

    public LinkedList getCommaExpression()
    {
        return _commaExpression_;
    }

    public void setCommaExpression(List list)
    {
        _commaExpression_.clear();
        _commaExpression_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_comparison_)
            + toString(_commaExpression_);
    }

    void removeChild(Node child)
    {
        if(_comparison_ == child)
        {
            _comparison_ = null;
            return;
        }

        if(_commaExpression_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_comparison_ == oldChild)
        {
            setComparison((PComparison) newChild);
            return;
        }

        for(ListIterator i = _commaExpression_.listIterator(); i.hasNext();)
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

    private class CommaExpression_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PCommaExpression node = (PCommaExpression) o;

            if((node.parent() != null) &&
                (node.parent() != AAssertComparisonStatement.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != AAssertComparisonStatement.this))
            {
                node.parent(AAssertComparisonStatement.this);
            }

            return node;
        }
    }
}