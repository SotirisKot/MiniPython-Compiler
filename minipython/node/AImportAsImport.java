/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.node;

import java.util.*;
import minipython.analysis.*;

public final class AImportAsImport extends PImport
{
    private PModule _module_;
    private final LinkedList _asIdentifier_ = new TypedLinkedList(new AsIdentifier_Cast());
    private final LinkedList _commaModuleAsId_ = new TypedLinkedList(new CommaModuleAsId_Cast());

    public AImportAsImport()
    {
    }

    public AImportAsImport(
        PModule _module_,
        List _asIdentifier_,
        List _commaModuleAsId_)
    {
        setModule(_module_);

        {
            this._asIdentifier_.clear();
            this._asIdentifier_.addAll(_asIdentifier_);
        }

        {
            this._commaModuleAsId_.clear();
            this._commaModuleAsId_.addAll(_commaModuleAsId_);
        }

    }
    public Object clone()
    {
        return new AImportAsImport(
            (PModule) cloneNode(_module_),
            cloneList(_asIdentifier_),
            cloneList(_commaModuleAsId_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAImportAsImport(this);
    }

    public PModule getModule()
    {
        return _module_;
    }

    public void setModule(PModule node)
    {
        if(_module_ != null)
        {
            _module_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _module_ = node;
    }

    public LinkedList getAsIdentifier()
    {
        return _asIdentifier_;
    }

    public void setAsIdentifier(List list)
    {
        _asIdentifier_.clear();
        _asIdentifier_.addAll(list);
    }

    public LinkedList getCommaModuleAsId()
    {
        return _commaModuleAsId_;
    }

    public void setCommaModuleAsId(List list)
    {
        _commaModuleAsId_.clear();
        _commaModuleAsId_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_module_)
            + toString(_asIdentifier_)
            + toString(_commaModuleAsId_);
    }

    void removeChild(Node child)
    {
        if(_module_ == child)
        {
            _module_ = null;
            return;
        }

        if(_asIdentifier_.remove(child))
        {
            return;
        }

        if(_commaModuleAsId_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_module_ == oldChild)
        {
            setModule((PModule) newChild);
            return;
        }

        for(ListIterator i = _asIdentifier_.listIterator(); i.hasNext();)
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

        for(ListIterator i = _commaModuleAsId_.listIterator(); i.hasNext();)
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

    private class AsIdentifier_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PAsIdentifier node = (PAsIdentifier) o;

            if((node.parent() != null) &&
                (node.parent() != AImportAsImport.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != AImportAsImport.this))
            {
                node.parent(AImportAsImport.this);
            }

            return node;
        }
    }

    private class CommaModuleAsId_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PCommaModuleAsId node = (PCommaModuleAsId) o;

            if((node.parent() != null) &&
                (node.parent() != AImportAsImport.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != AImportAsImport.this))
            {
                node.parent(AImportAsImport.this);
            }

            return node;
        }
    }
}
