/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class APowerExpression extends PExpression
{
    private PExpression _l_;
    private PExpression _r_;

    public APowerExpression()
    {
    }

    public APowerExpression(
        PExpression _l_,
        PExpression _r_)
    {
        setL(_l_);

        setR(_r_);

    }
    public Object clone()
    {
        return new APowerExpression(
            (PExpression) cloneNode(_l_),
            (PExpression) cloneNode(_r_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPowerExpression(this);
    }

    public PExpression getL()
    {
        return _l_;
    }

    public void setL(PExpression node)
    {
        if(_l_ != null)
        {
            _l_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _l_ = node;
    }

    public PExpression getR()
    {
        return _r_;
    }

    public void setR(PExpression node)
    {
        if(_r_ != null)
        {
            _r_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _r_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_l_)
            + toString(_r_);
    }

    void removeChild(Node child)
    {
        if(_l_ == child)
        {
            _l_ = null;
            return;
        }

        if(_r_ == child)
        {
            _r_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_l_ == oldChild)
        {
            setL((PExpression) newChild);
            return;
        }

        if(_r_ == oldChild)
        {
            setR((PExpression) newChild);
            return;
        }

    }
}
